# Hive Advanced

## 1. 调优

### 1.1 Explain 查看执行计划

`EXPLAIN [EXTENDED | DEPENDENCY | AUTHORIZATION] query-sql` 

### 1.2 建表优化

#### 1.2.1 分区表

分区表对应 HDFS 上一个独立的文件夹，该文件夹下是分区的所有文件

Hive 中的分区就是分目录，把一个大的数据集根据业务需要分割成小的数据集

查询时通过 WHERE 子句中的表达式选择查询所需要指定的分区，提高查询效率

分区字段不能是表中已经存在的数据，可以将分区字段看作表的伪列

加载数据时必须指定分区

#### 1.2.2 分桶表

分区提供一个隔离数据和优化查询的便利的方式

而分桶是将数据集分解成更容易管理的若干部分的一种方法

分区针对的是路径，分桶针对的是数据文件

=> 抽样查询：`TABLESAMPLE(BUCKET X OUT OF y)` 

#### 1.2.3 合适的文件格式

TEXTFILE，SEQUENCEFILE，ORC，PARQUET

#### 1.2.4 合适的压缩方式

DEFLATE，Gzip，bzip2，LZO，Snappy

### 1.3 HQL 语法优化

#### 1.3.1 列裁剪与分区裁剪

列裁剪就是在查询时只读取需要的列，分区裁剪就是只读取需要的分区

当数据量很大的时候，如果 `select *` 或者不指定分区，全列扫描和全表扫描效率都很低

#### 1.3.2 Group by

默认情况下，Map 阶段同一 key 数据分发给一个 Reduce，当一个 key 数据过大时就数据倾斜了

可以在 Map 端开启聚合操作

```shell
set hive.map.aggr = true;
set hive.groupby.mapaggr.checkinterval = 100000;
set hive.groupby.skewindata = true;
会产生两个 MR Job
```

#### 1.3.3 Vectorization

矢量计算技术，在计算类似 scan，filter，aggregation 的时候，vectorization 技术以设置批处理的增加大小为 1024 行代词来达到比单条记录获得更高的效率

```shell
set hive.vectorized.execution.enabled = true;
set hive.vectorized.execution.reduce.enabled = true;
```

#### 1.3.4 多重模式

将 insert / select ... from 改为 from table insert ... insert ... insert ... / select ... select ... select ...

#### 1.3.5 in/exist 语句

--in / exist 可以改为 left semi join 

#### 1.3.6 CBO 优化

Join 表的时候的顺序关系：前面的表都会被加载到内存中，后面的表进行磁盘扫描

Hive 1.1.0 后默认开启 Cost Based Optimizer 来对 HQL 执行进行优化

CBO，成本优化器，代价最小的执行计划就是最好的执行计划

```shell
set hive.cbo.enable = true;
set hive.compute.query.using.stats = true;
set hive.stats.fetch.column.stats = true;
set hive.stats.fetch.partition.stats = true;
```

#### 1.3.7 谓词下推

将 where 谓词逻辑都尽可能提前执行，减少下游处理的数据量

对应的逻辑优化器：`PredicatePushDown` ，配置项为 `hive.optimize.ppd = true` ，默认为 true 

#### 1.3.8 MapJoin

MapJoin 是将 Join 双方比较小的表直接分发到各个 Map 进程的内存中，在 Map 进程中进行 Join 操作，这样就不用进行 Reduce 步骤，从而提高了速度，如果不指定 MapJoin 或者不符合 MapJoin 条件，那么 Hive 解析器会将 Join 操作转换成 Common Join

```shell
set hive.auto.convert.join = true;
awr hive.mapjoin.smalltable.filesize = 25000000;
```

#### 1.3.9 大表，大表 SMB Join (Sort Merge Bucket Join)

```shell
set hive.optimize.bucketmapjoin = true;
set hive.optimize.bucketmapjoin.sortedmerge = true;
set hive.input.format = org.apache.hadoop.hive.ql.io.BucketizedHiveInputFormat;
```

#### 1.3.10 笛卡尔积

Join 不加 on 或者无效的 on 条件，只能使用一个 Reducer 来完成笛卡尔积

```shell
set hive.mapred.mode = strict;
```

### 1.4 数据倾斜

从 HQL 角度可以把数据倾斜分为

- 单表携带了 `groupby` 字段的查询
- 两表 `join` 的查询

#### 1.4.1 单表数据倾斜优化

__设置参数__ 

当任务中存在 groupby 操作同时聚合函数为 count 或者 sum 

```shell
set hive.map.aggr = true;
set hive.groupby.mapaggr.checkinterval = 100000;
set hive.groupby.skewindata = true;
# 数据倾斜时的负载均衡
```

__增加 Reduce 数量（多个 Key 同时导致数据倾斜）__ 

- 方法一

    ```shell
    set hive.exec.reducers.bytes.per.reducer = 256000000;
    set hive.exec.reducers.max = 1009;
    # N = min(param2, input/param1)   param1 : 256M, param2 : 1009 ↑
    ```

- 方法二

    ```shell
    # mapred-default.xml
    set mapreduce.job.reduces = 15;
    ```

#### 1.4.2 Join 数据倾斜优化

__设置参数__ 

```shell
# Join 的键对应的记录条数超过这个值则会进行分拆
set hive.skewjoin.key = 100000;
# Join 出现倾斜则设置为 true
set hive.optimize.skewjoin = false;
```

`hive,skewjoin.key` 会将倾斜的 key 写入对应文件中，然后启动另一个 Job 做 MapJoin 生成结果

```shell
set hive.skewjoin.mapjoin.map.tasks = 10000;
# 控制另一个 Job 的 Mapper 的数量
```

__MapJoin__ 

=> 1.3.9

### 1.5 Job 优化

#### 1.5.1 Map 优化

__复杂文件增加 Map 数__ 

当 input 文件很大，任务逻辑复杂，map 执行慢时，可以增加 map 数

根据 `computeSliteSize(Math.max(minSize, Math.min(maxSize, blocksize)))` = 128

调整 maxSize 最大值，让 maxSize 低于 blocksize 就可以增加 map 数

__小文件合并__ 

map 执行前合并小文件，减少 map 数：CombineHiveInputFormat 具有对小文件进行合并的功能

```shell
set hive.input.format = org.apache.hadoop.hive.ql.io.CombineHiveInputFormat;
```

MR 结束时合并小文件的设置

```shell
# map only 结束时合并文件
set hive.merge.mapfiles = true;
# mr 结束时合并
set hive.merge.mapredfiles = true;
# 合并文件大小 256M
set hive.merge.size.per.task = 268435456;
# 当输出文件小于该值时，启动一个独立的 map-reduce 任务进行 merge
set hive.merge.smallfiles.avgsize = 16777216;
```

__Map 端聚合__ 

```shell
set hive.map.aggr = true;
```

__推测执行__ 

```shell
set mapred.map.tasks.speculative.execution = true;
```

#### 1.5.2 Reduce 优化

__合理设置 reduce 数__ 

=> 1.4.1 增加 reduce 个数

__推测执行__ 

```shell
set mapred.map.tasks.speculative.execution = true;
set hive.mapred.reduce.tasks.speculative.execution = true;
```

#### 1.5.3 HIve 任务整体优化

__Fetch 抓取__ 

Fetch 指在某些情况下的查询可以不必使用 MapReduce 计算

`hive-default.xml.template` 

```xml
<property>
	<name>hive.fetch.task.conversion</name>
    <value>more</value>
    <description>none, minimal, more   
    	none : 不启用
        minimal : SELECT STAR, FILTER on partition columns, LIMIT only
        more : SELECT, FILTER, LIMIT only (support TABLESAMPLE and virtual columns)
    </description>
</property>
```

__本地模式__ 

大多数 Hadoop Job 是需要 Hadoop 提供完整的可扩展性来处理大数据集的

Hive 可以通过本地模式在单台机器上处理所有的任务，对于小数据集，执行时间可以明显被缩短

```shell
set hive.exec.mode.local.auto = true;
set hive.exec.mode.local.auto.inputbytes.max = 50000000;
set hive.exec.mode.local.auto.input.files.max = 10;
```

__并行执行__ 

默认情况下 Hive 只会执行一个阶段，特定的 Job 可能包含众多阶段，这些阶段可能并非完全互相依赖的，可以并行执行

```shell
set hive.exec.parallel = true;
set hive.exec.parallel.thread.number = 16;
```

建议在数据量大，sql 长的时候使用

__严格模式__ 

防止危险操作

- 分区表不适用分区过滤

    ```shell
    set hive.strict.checks.no.partition.filter = true;
    ```

- 使用 order by 没有 limit 过滤

    ```shell
    set hive.strict.checks.orderby.no.limit = true;
    ```

- 笛卡尔积

    ```shell
    set hive.strict.checks.cartesian.product = true;
    ```

__JVM 重用__ （主要是小文件）

## 2. 源码

### 2.1 Hive 核心组成

=> Hive 基础

### 2.2 HQL 变成 MR 任务流程说明

1. 进入程序，利用 Antlr 框架定义 HQL 的语法规则，对 HQL 完成词法语法解析，将 HQL 转换为 AST（抽象语法树）
2. 遍历 AST，抽象出查询的基本组成单元 QueryBlock（查询块），可以理解为最小的查询执行单元
3. 遍历 QueryBlock，将其转换为 OperatorTree（操作树，也就是逻辑执行计划），可以理解为不可拆分的一个逻辑执行单元
4. 使用逻辑优化器对 OperatorTree（操作树）进行逻辑优化（例如：合并不必要的 ReduceSinkOperator，减少 Shuffle 数据量）
5. 遍历 OperatorTree，转换为 TaskTree，也就是翻译为 MR 任务流程，将逻辑执行计划转换为物理执行计划
6. 使用物理优化器对 TaskTree 进行物理优化
7. 生成最终的执行计划，提交任务到 Hadoop 集群运行

流程

- $HIVE_HOME/bin/hive select...
- CliDriver
    - 解析客户端 “-e -f” 等等参数
    - 定义标准输入输出流
    - 然后按照 `;` 切分 HQL 语句
- Driver
    - 将 HQL 语句转换为 AST（`PaserUtil.paser()` => PaserDriver）
        - 将 HQL 语句转换为 Token
        - 对 Token 进行解析
    - 将 AST 转换为 TaskTree（`sem.analyse()` => SemanticAnalyzer）
        - 将 AST 转换为 QueryBlock
        - 将 QueryBlock 转换为 OperatorTree
        - OperatorTree 进行逻辑优化
        - 生成 TaskTree
        - TaskTree 执行物理优化
    - 提交任务执行（`TaskRunner.runSequential()` => Exec.Driver）
        - 获取 MR 临时任务
        - 定义 Partitioner
        - 定义 Mapper 和 Reducer
        - 实例化 Job
        - 提交 Job

### 2.3 程序入口 - CliDriver

```java
public class CliDriver {
    public static void main(String[] args) throws Exception {
        int ret = new CliDriver().run(args);
        System.exit(ret);
    }

    public int run(String[] args) throws Exception {
        OptionsProcessor oproc = new OptionsProcessor();
        if (!oproc.process_stage1(args)) {
            return 1;
        }
        // stdin stdout stdinfo stderr
        ss.in = System.in;
        try {
            ss.out = new PrintStream(System.out, true, "UTF-8");
            ss.info = new PrintStream(System.err, true, "UTF-8");
            ss.err = new CachingPrintStream(System.err, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return 3;
        }
        if (!oproc.process_stage2(ss)) {
            return 2;
        }
        // ...
        // 解析输入的参数
        HiveConf conf = ss.getConf();
        for (Map.Entry<Object, Object> item : ss.cmdProperties.entrySet()) {
            conf.set((String) item.getKey(), (String) item.getValue());
            ss.getOverriddenConfigurations().put((String) item.getKey(), (String) item.getValue());
        }
        // 命令行打印的前缀
        prompt = conf.getVar(HiveConf.ConfVars.CLIPROMPT);
        // ...
        try {
            return executeDriver(ss, conf, oproc);
        } finally {
            ss.resetThreadName();
            ss.close();
        }
    }
    
    private int executeDriver(CliSessionState ss, HiveConf conf, OptionsProcessor oproc) throws Exception {
        // ...
        // 判断执行程序的引擎是否是 MR
        if ("mr".equals(HiveConf.getVar(conf, ConfVars.HIVE_EXECUTION_ENGINE))) {
            console.printInfo(HiveConf.generateMrDeprecationWarning());
        }
        // ...
        // 读取命令行内容
        while ((line = reader.readLine(curPrompt + "> ")) != null) {
            // 空的直接换行
            if (!prefix.equals("")) {
                prefix += '\n';
            }
            // 注释
            if (line.trim().startsWith("--")) {
                continue;
            }
            // 是否以分号结尾
            if (line.trim().endsWith(";") && !line.trim().endsWith("\\;")) {
                line = prefix + line;
                // 处理行
                ret = cli.processLine(line, true);
                prefix = "";
                curDB = getFormattedDb(conf, ss);
                curPrompt = prompt + curDB;
                dbSpaces = dbSpaces.length() == curDB.length() ? dbSpaces : spacesForString(curDB);
            } else {
                // 没有分号，追加
                prefix = prefix + line;
                curPrompt = prompt2 + dbSpaces;
                continue;
            }
        }
        // ...
    }
    
    public int processLine(String line, boolean allowInterrupting) {
        // ...
        try {
            // ...
            // 通过分号分隔命令行
            List<String> commands = splitSemiColon(line);
            String command = "";
            for (String oneCmd : commands) {
                if (StringUtils.endsWith(oneCmd, "\\")) {
                    command += StringUtils.chop(oneCmd) + ";";
                    continue;
                } else {
                    command += oneCmd;
                }
                // 不管空行
                if (StringUtils.isBlank(command)) {
                    continue;
                }

                ret = processCmd(command);
                command = "";
                // ...
            }
            return lastRet;
        } finally {
            // ...
        }
    }
    
    public int processCmd(String cmd) {
        // quit exit 退出
        if (cmd_trimmed.toLowerCase().equals("quit") || cmd_trimmed.toLowerCase().equals("exit")) {
            ss.close();
            System.exit(0);
        // source 执行 hql 文件
        } else if (tokens[0].equalsIgnoreCase("source")) {
            // ...
        // ! 开头执行 shell 命令
        } else if (cmd_trimmed.startsWith("!")) {
            // ...
        // hql
        }  else { // local mode
            try {
                try (CommandProcessor proc = CommandProcessorFactory.get(tokens, (HiveConf) conf)) {
                    if (proc instanceof IDriver) {
                        // 真正解析 hql
                        ret = processLocalCmd(cmd, proc, ss);
                    } else {
                        ret = processLocalCmd(cmd_trimmed, proc, ss);
                    }
                }
            } catch (SQLException e) {
                // ...
            } catch (Exception e) {
                /
            }
        }
        // ...
    }
    
    int processLocalCmd(String cmd, CommandProcessor proc, CliSessionState ss) {
        // ...
        if (proc != null) {
            if (proc instanceof IDriver) {
                IDriver qp = (IDriver) proc;
                // 获取输出流
                PrintStream out = ss.out;
                // 获取当前时间
                long start = System.currentTimeMillis();
                // ...
				// 运行 command
                ret = qp.run(cmd).getResponseCode();
                // ...
                // 获取运行完时间
                long end = System.currentTimeMillis();
                double timeTaken = (end - start) / 1000.0;
                ArrayList<String> res = new ArrayList<String>();
                // 打印头信息
                printHeader(qp, out);
                int counter = 0;
                try {
                    if (out instanceof FetchConverter) {
                        ((FetchConverter) out).fetchStarted();
                    }
                    while (qp.getResults(res)) {
                        // ...
                        // 统计查询结果
                        counter += res.size();
                        res.clear();
                        if (out.checkError()) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    // ...
                }
                // ...
                // 输出信息
                console.printInfo(
                    "Time taken: " + timeTaken + " seconds" + (counter == 0 ? "" : ", Fetched: " + counter + " row(s)"));
            } else {
                // ...
            }
        }
        // ...
    }
}

public class OptionsProcessor {
    // 检查 hiveconf, hive.root.logger, define, hivevar 参数合法性
    public boolean process_stage1(String[] argv) {
        try {
            commandLine = new GnuParser().parse(options, argv);
            Properties confProps = commandLine.getOptionProperties("hiveconf");
            for (String propKey : confProps.stringPropertyNames()) {
                if (propKey.equalsIgnoreCase("hive.root.logger")) {
                    CommonCliOptions.splitAndSetLogger(propKey, confProps);
                } else {
                    System.setProperty(propKey, confProps.getProperty(propKey));
                }
            }
            Properties hiveVars = commandLine.getOptionProperties("define");
            //
            Properties hiveVars2 = commandLine.getOptionProperties("hivevar");
            //
        } catch (ParseException e) {
            // 
        }
        return true;
    }
    
    // 解析命令行参数
    public boolean process_stage2(CliSessionState ss) {
        ss.getConf();
        if (commandLine.hasOption('H')) {
            printUsage();
            return false;
        }
        ss.setIsSilent(commandLine.hasOption('S'));
        ss.database = commandLine.getOptionValue("database");
        ss.execString = commandLine.getOptionValue('e');
        ss.fileName = commandLine.getOptionValue('f');
        ss.setIsVerbose(commandLine.hasOption('v'));
        String[] initFiles = commandLine.getOptionValues('i');
        if (null != initFiles) {
            ss.initFiles = Arrays.asList(initFiles);
        }
        if (ss.execString != null && ss.fileName != null) {
            System.err.println("The '-e' and '-f' options cannot be specified simultaneously");
            printUsage();
            return false;
        }
        if (commandLine.hasOption("hiveconf")) {
            Properties confProps = commandLine.getOptionProperties("hiveconf");
            for (String propKey : confProps.stringPropertyNames()) {
                ss.cmdProperties.setProperty(propKey, confProps.getProperty(propKey));
            }
        }
        return true;
    }
}

public class HiveConf extends Configuration {
    public static enum ConfVars {
        // ...
        CLIPROMPT("hive.cli.prompt", "hive",
                  "Command line prompt configuration value. Other hiveconf can be used in this configuration value. \n" +
                  "Variable substitution will only be invoked at the Hive CLI startup."),
        HIVE_EXECUTION_ENGINE("hive.execution.engine", "mr", new StringSet(true, "mr", "tez", "spark"),
        "Chooses execution engine. Options are: mr (Map reduce, default), tez, spark. While MR\n" +
        "remains the default engine for historical reasons, it is itself a historical engine\n" +
        "and is deprecated in Hive 2 line. It may be removed without further warning."),
        // ...
    }
}
```

### 2.4 准备编译和执行 cmd

```java
public class Driver implements IDriver {
    public CommandProcessorResponse run(String command, boolean alreadyCompiled) {
        try {
            // 准备执行编译等流程
            runInternal(command, alreadyCompiled);
            return createProcessorResponse(0);
        } catch (CommandProcessorResponse cpr) {
            // ...
        }
    }
    
    private void runInternal(String command, boolean alreadyCompiled) throws CommandProcessorResponse {
        // ...
        try {
            // ... 
            // 开始编译
            if (!alreadyCompiled) {
                compileInternal(command, true);
                perfLogger = SessionState.getPerfLogger();
            } else {
                // ...
            }
            // ...
            try {
                if (!isValidTxnListState()) {
                    // 如果没有编译，进行编译
                    if (!alreadyCompiled) {
                        compileInternal(command, true);
                    } else {
                        plan.setQueryStartTime(queryDisplay.getQueryStartTime());
                    }
                    // ...
                }
            } catch (LockException e) {
                throw handleHiveException(e, 13);
            }

            try {
                // 执行
                execute();
            } catch (CommandProcessorResponse cpr) {
                rollback(cpr);
                throw cpr;
            }
            // ...
            
        } finally {
            // ...
        }
    }
    
    private void compileInternal(String command, boolean deferClose) throws CommandProcessorResponse {
        // ...
        try {
            // 开始编译
            compile(command, true, deferClose);
        } catch (CommandProcessorResponse cpr) {
            // ...
        } finally {
            compileLock.unlock();
        }
        // ...
    }
    
    private void compile(String command, boolean resetTaskIds, boolean deferClose) throws CommandProcessorResponse {
        
    }
}
```

### 2.5 HQL 生成 AST 抽象语法树

```java
public class Driver implements IDriver {
    private void compile(String command, boolean resetTaskIds, boolean deferClose) throws CommandProcessorResponse {
        // ...
        try {
            ASTNode tree;
            try {
                // 解析器
                tree = ParseUtils.parse(command, ctx);
            } catch (ParseException e) {
                parseError = true;
                throw e;
            } finally {
                hookRunner.runAfterParseHook(command, parseError);
            }
        }
        // ...
        BaseSemanticAnalyzer sem = SemanticAnalyzerFactory.get(queryState, tree);
        // ...
        // 编译器和优化器
        sem.analyze(tree, ctx);
        // ...
    }
}

public final class ParseUtils {
    public static ASTNode parse(
        String command, Context ctx, String viewFullyQualifiedName) throws ParseException {
        ParseDriver pd = new ParseDriver();
        ASTNode tree = pd.parse(command, ctx, viewFullyQualifiedName);
        tree = findRootNonNullToken(tree);
        handleSetColRefs(tree);
        return tree;
    }
}

public class ParseDriver {
    public ASTNode parse(String command, Context ctx, String viewFullyQualifiedName) throws ParseException {
        // ...
        // 构建词法解析器 Antlr 框架
        HiveLexerX lexer = new HiveLexerX(new ANTLRNoCaseStringStream(command));
        // 将 HQL 语句转换为 Token
        TokenRewriteStream tokens = new TokenRewriteStream(lexer);
        // ...
        // 对 Token 进行解析
        HiveParser parser = new HiveParser(tokens);
        // ...
        try {
            // 进行语法解析，生成最终的 AST
            r = parser.statement();
        } catch (RecognitionException e) {
            e.printStackTrace();
            throw new ParseException(parser.errors);
        }
        // ...
        ASTNode tree = (ASTNode) r.getTree();
        tree.setUnknownTokenBoundaries();
        return tree;
    }
}
```

Hive 用 Antlr 实现 SQL 词法和语法解析

Hive 中 5 个语法规则文件

- HiveLexer.g
- SelectClauseParser.g
- FromClauseParser.g
- IdentifiersParser.g
- HiveParser.g

### 2.6 将 AST 转换为 TaskTree

```java
public abstract class BaseSemanticAnalyzer {
    public void analyze(ASTNode ast, Context ctx) throws SemanticException {
        initCtx(ctx);
        init(true);
        analyzeInternal(ast);
    }
}

public class SemanticAnalyzer extends BaseSemanticAnalyzer {
    @Override
    @SuppressWarnings("nls")
    public void analyzeInternal(ASTNode ast) throws SemanticException {
        analyzeInternal(ast, new PlannerContextFactory() {
            @Override
            public PlannerContext create() {
                return new PlannerContext();
            }
        });
    }
    
    void analyzeInternal(ASTNode ast, PlannerContextFactory pcf) throws SemanticException {
        // 1. Generate Resolved Parse tree from syntax tree
        boolean needsTransform = needsTransform();
        // 将 AST 转换为 QueryBlock
        // 将 QueryBlock 转换为 OperatorTree
        // ...
        // 2. Gen OP Tree from resolved Parse Tree
        Operator sinkOp = genOPTree(ast, plannerCtx);
        // ...
        // 3. Deduce Resultset Schema
        if (createVwDesc != null && !this.ctx.isCboSucceeded()) {
            resultSchema = convertRowSchemaToViewSchema(opParseCtx.get(sinkOp).getRowResolver());
        } else {
            // resultSchema will be null if
            // (1) cbo is disabled;
            // (2) or cbo is enabled with AST return path (whether succeeded or not,
            // resultSchema will be re-initialized)
            // It will only be not null if cbo is enabled with new return path and it
            // succeeds.
            if (resultSchema == null) {
                resultSchema = convertRowSchemaToResultSetSchema(
                    opParseCtx.get(sinkOp).getRowResolver(),
                    HiveConf.getBoolVar(conf, HiveConf.ConfVars.HIVE_RESULTSET_USE_UNIQUE_COLUMN_NAMES));
            }
        }
        // 4. Generate Parse Context for Optimizer & Physical compiler
        copyInfoToQueryProperties(queryProperties);
        // ...
        // 5. Take care of view creation
        if (createVwDesc != null) {
            // ...
        }
        // ...
        // 6. Generate table access stats if required
        if (HiveConf.getBoolVar(this.conf, HiveConf.ConfVars.HIVE_STATS_COLLECT_TABLEKEYS)) {
            TableAccessAnalyzer tableAccessAnalyzer = new TableAccessAnalyzer(pCtx);
            setTableAccessInfo(tableAccessAnalyzer.analyzeTableAccess());
        }
        // 7. Perform Logical optimization
        // ...
        Optimizer optm = new Optimizer();
        optm.setPctx(pCtx);
        optm.initialize(conf);
        // OperatorTree 进行逻辑优化
        // 生成 TaskTree
        pCtx = optm.optimize();
        // ...
        // 8. Generate column access stats if required - wait until column pruning
        // takes place during optimization
        boolean isColumnInfoNeedForAuth = SessionState.get().isAuthorizationModeV2()
            && HiveConf.getBoolVar(conf, HiveConf.ConfVars.HIVE_AUTHORIZATION_ENABLED);
        // ...
        // 9. Optimize Physical op tree & Translate to target execution engine (MR, TEZ, SPAR)
        if (!ctx.getExplainLogical()) {
            TaskCompiler compiler = TaskCompilerFactory.getCompiler(conf, pCtx);
            compiler.init(queryState, console, db);
            // TaskTree 执行物理优化
            compiler.compile(pCtx, rootTasks, inputs, outputs);
            fetchTask = pCtx.getFetchTask();
        }
        //find all Acid FileSinkOperatorS
        QueryPlanPostProcessor qp = new QueryPlanPostProcessor(rootTasks, acidFileSinks, ctx.getExecutionId());
        // 10. Attach CTAS/Insert-Commit-hooks for Storage Handlers
        final Optional<TezTask> optionalTezTask =
            rootTasks.stream().filter(task -> task instanceof TezTask).map(task -> (TezTask) task)
            .findFirst();
        // ...
        // 11. put accessed columns to readEntity
        if (HiveConf.getBoolVar(this.conf, HiveConf.ConfVars.HIVE_STATS_COLLECT_SCANCOLS)) {
            putAccessedColumnsToReadEntity(inputs, columnAccessInfo);
        }
        // ...
    }
}

public class Optimizer {
    // Invoke all the transformations one-by-one, and alter the query plan.
    public ParseContext optimize() throws SemanticException {
        for (Transform t : transformations) {
            t.beginPerfLogging();
            pctx = t.transform(pctx);
            t.endPerfLogging(t.toString());
        }
        return pctx;
    }
}

public abstract class TaskCompiler {
    public void compile(final ParseContext pCtx,
      final List<Task<? extends Serializable>> rootTasks,
      final HashSet<ReadEntity> inputs, final HashSet<WriteEntity> outputs) throws SemanticException {
        // ...
        optimizeOperatorPlan(pCtx, inputs, outputs);
    }
    
    protected void optimizeOperatorPlan(ParseContext pCtxSet, Set<ReadEntity> inputs,
                                        Set<WriteEntity> outputs) throws SemanticException {}
}
```

常用优化器

- SimplePredicatePushDown
- GroupByOptimizer
- PartitionConditionRemover
- SkewJoinOptimizer
- MapJoinProcessor
- ReduceSinkDeDuplication

### 2.7 提交任务并执行

```java
public class Driver implements IDriver {
    private void runInternal(String command, boolean alreadyCompiled) throws CommandProcessorResponse {
        // ...
        try {
            execute();
        } catch (CommandProcessorResponse cpr) {
            rollback(cpr);
            throw cpr;
        }
        // ...
    }
    
    private void execute() throws CommandProcessorResponse {
        // ...
        try {
            // ...
            // 构建任务：根据任务树构建 MRJob
            int jobs = mrJobs + Utilities.getTezTasks(plan.getRootTasks()).size()
                + Utilities.getSparkTasks(plan.getRootTasks()).size();
            // ...
            while (driverCxt.isRunning()) {
                // Launch upto maxthreads tasks
                Task<? extends Serializable> task;
                while ((task = driverCxt.getRunnable(maxthreads)) != null) {
                    // 启动任务
                    TaskRunner runner = launchTask(task, queryId, noName, jobname, jobs, driverCxt);
                    if (!runner.isRunning()) {
                        break;
                    }
                }
                // ...
            }
        }
        // ...
        // 结束打印结果
        if (console != null) {
            console.printInfo("OK");
        }
    }
    
    private TaskRunner launchTask(Task<? extends Serializable> tsk, String queryId, boolean noName,
                                  String jobname, int jobs, DriverContext cxt) throws HiveException {
		// ...
        // 添加任务
        TaskRunner tskRun = new TaskRunner(tsk);
        cxt.launching(tskRun);
        // ...
        // 真正运行任务
        if (HiveConf.getBoolVar(conf, HiveConf.ConfVars.EXECPARALLEL) && tsk.canExecuteInParallel()) {
            // Launch it in the parallel mode, as a separate thread only for MR tasks
            if (LOG.isInfoEnabled()){
                LOG.info("Starting task [" + tsk + "] in parallel");
            }
            // 并行
            tskRun.start();
        } else {
            if (LOG.isInfoEnabled()){
                LOG.info("Starting task [" + tsk + "] in serial mode");
            }
            tskRun.runSequential();
        }
    }
}

public class DriverContext {
    public synchronized void launching(TaskRunner runner) throws HiveException {
        checkShutdown();
        running.add(runner);
    }
}

public class TaskRunner extends Thread {
    @Override
    public void run() {
        runner = Thread.currentThread();
        try {
            SessionState.start(ss);
            runSequential();
        } finally {
            // ...
        }
    }

    public void runSequential() {
        int exitVal = -101;
        try {
            exitVal = tsk.executeTask(ss == null ? null : ss.getHiveHistory());
        } catch (Throwable t) {
            // ...
        }
        // ...
    }
}

public abstract class Task<T extends Serializable> implements Serializable, Node {
    public int executeTask(HiveHistory hiveHistory) {
        try {
            // ...
            int retval = execute(driverContext);
            // ...
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
}

public class ExecDriver extends Task<MapredWork> implements Serializable, HadoopJobExecHook {
    @Override
    public int execute(DriverContext driverContext) {
        // ...
        job.setOutputFormat(HiveOutputFormatImpl.class);
        job.setMapRunnerClass(ExecMapRunner.class);
        job.setMapperClass(ExecMapper.class);
        job.setMapOutputKeyClass(HiveKey.class);
        job.setMapOutputValueClass(BytesWritable.class);
        try {
            String partitioner = HiveConf.getVar(job, ConfVars.HIVEPARTITIONER);
            job.setPartitionerClass(JavaUtils.loadClass(partitioner));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        propagateSplitSettings(job, mWork);
        job.setNumReduceTasks(rWork != null ? rWork.getNumReduceTasks().intValue() : 0);
        job.setReducerClass(ExecReducer.class);
        // ...
        job.setBoolean(MRJobConfig.REDUCE_SPECULATIVE, useSpeculativeExecReducers);
        // ...
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        // ...
        if (noName) {
            job.set(MRJobConfig.JOB_NAME, "JOB" + Utilities.randGen.nextInt());
        }
        try{
            // ...
            jc = new JobClient(job);
            // ...
            rj = jc.submitJob(job);
            // ...
        }
    }
}
```

### 2.8 Debug

```shell
-agentlib:jdwp=transport=dt_socket, server=y, suspend=n, address=8000
```

## 3. 面试题

间隔连续

- 开窗数据下推
- diff
- sum if
- 分组

间隔交叉问题

- 开窗最大值到上一行
- 如果大则不需要操作，否则替换
- diff
- sum if

交叉数据最大值

- 对开始点的数据标 1，结束点标 2
- 排序
- 算法：求连续最大和
