# Hadoop 源码解析

## 1. NameNode 启动源码解析

- NameNode 工作机制

    1. 加载编辑日志和镜像文件到内存
    2. 元数据的增删请求
    3. 记录操作日志，滚动日志
    4. 内存数据的增删查改

- NameNode 位置

    ```java
    package org.apache.hadoop.hdfs.server.namenode;
    
    @InterfaceAudience.Private
    public class NameNode extends ReconfigurableBase implements NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {}
    ```

### 1.1 启动 9870 端口

```java
@InterfaceAudience.Private
public class NameNode extends ReconfigurableBase implements NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {
    public static void main(String argv[]) throws Exception {
        // ...
        try {
            // ...
            NameNode namenode = createNameNode(argv, null);
            // ...
        } catch (Throwable e) {
            // ...
        }
    }

    public static NameNode createNameNode(String argv[], Configuration conf)
        throws IOException {
        // ...
        return new NameNode(conf);
    }

    public NameNode(Configuration conf) throws IOException {
        this(conf, NamenodeRole.NAMENODE);
    }

    protected NameNode(Configuration conf, NamenodeRole role) 
        throws IOException {
        // ...
        try {
            initializeGenericKeys(conf, nsId, namenodeId);
            initialize(getConf());
        } catch (/*...*/) {
            // ...
        }
        // ...
    }

    protected void initialize(Configuration conf) throws IOException {
        // ...
        if (NamenodeRole.NAMENODE == role) {
            startHttpServer(conf);
        }
    }

    private void startHttpServer(final Configuration conf) throws IOException {
        httpServer = new NameNodeHttpServer(conf, this, getHttpServerBindAddress(conf));
        httpServer.start();
        httpServer.setStartupProgress(startupProgress);
    }

    protected InetSocketAddress getHttpServerBindAddress(Configuration conf) {
        InetSocketAddress bindAddress = getHttpServerAddress(conf);
        // ...
    }

    protected InetSocketAddress getHttpServerAddress(Configuration conf) {
        return getHttpAddress(conf);
    }

    public static InetSocketAddress getHttpAddress(Configuration conf) {
        return  NetUtils.createSocketAddr(
            conf.getTrimmed(DFS_NAMENODE_HTTP_ADDRESS_KEY, 
                            DFS_NAMENODE_HTTP_ADDRESS_DEFAULT));
    }
}

@InterfaceAudience.Private
public class DFSConfigKeys extends CommonConfigurationKeys {
    public static final String  DFS_NAMENODE_HTTP_ADDRESS_KEY =
      HdfsClientConfigKeys.DFS_NAMENODE_HTTP_ADDRESS_KEY;
    public static final String  DFS_NAMENODE_HTTP_ADDRESS_DEFAULT = 
        "0.0.0.0:" + DFS_NAMENODE_HTTP_PORT_DEFAULT;
    public static final int     DFS_NAMENODE_HTTP_PORT_DEFAULT =
      HdfsClientConfigKeys.DFS_NAMENODE_HTTP_PORT_DEFAULT;
}

@InterfaceAudience.Private
public interface HdfsClientConfigKeys {
    String  DFS_NAMENODE_HTTP_ADDRESS_KEY = "dfs.namenode.http-address";
    int     DFS_NAMENODE_HTTP_PORT_DEFAULT = 9870;
}

// httpServer.start();
@InterfaceAudience.Private
public class NameNodeHttpServer {

    void start() throws IOException {
        // ...
        HttpServer2.Builder builder = DFSUtil.httpServerTemplateForNNAndJN(
            conf, httpAddr, httpsAddr, "hdfs",
            DFSConfigKeys.DFS_NAMENODE_KERBEROS_INTERNAL_SPNEGO_PRINCIPAL_KEY,
            DFSConfigKeys.DFS_NAMENODE_KEYTAB_FILE_KEY);
        // ...
        httpServer = builder.build();
        // ...
        setupServlets(httpServer);
        httpServer.start();
    }
    
    private static void setupServlets(HttpServer2 httpServer) {
        httpServer.addInternalServlet("startupProgress", 
                                      StartupProgressServlet.PATH_SPEC, 
                                      StartupProgressServlet.class);
        httpServer.addInternalServlet("fsck", "/fsck",
                                      FsckServlet.class, true);
        httpServer.addInternalServlet("imagetransfer", 
                                      ImageServlet.PATH_SPEC,
                                      ImageServlet.class, true);
        httpServer.addInternalServlet(IsNameNodeActiveServlet.SERVLET_NAME,
                                      IsNameNodeActiveServlet.PATH_SPEC,
                                      IsNameNodeActiveServlet.class);
        httpServer.addInternalServlet("topology",
                                      NetworkTopologyServlet.PATH_SPEC, 
                                      NetworkTopologyServlet.class);
    }
}
```

### 1.2 加载镜像文件和编辑日志

```java
@InterfaceAudience.Private
public class NameNode extends ReconfigurableBase implements
    NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {
    protected void initialize(Configuration conf) throws IOException {
        // ...
        if (NamenodeRole.NAMENODE == role) {
            startHttpServer(conf);
        }
        loadNamesystem(conf);
		// ...
    }
    
    protected void loadNamesystem(Configuration conf) throws IOException {
        this.namesystem = FSNamesystem.loadFromDisk(conf);
    }
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements Namesystem, FSNamesystemMBean,
NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
    static FSNamesystem loadFromDisk(Configuration conf) throws IOException {
        checkConfiguration(conf);
        /**
              * @param conf Configuration
   			  * @param imageDirs Directories the image can be stored in.
		      * @param editsDirs Directories the editlog can be stored in.
		      */
        FSImage fsImage = new FSImage(conf,
                                      FSNamesystem.getNamespaceDirs(conf),
                                      FSNamesystem.getNamespaceEditsDirs(conf));
        // ...
        try {
            namesystem.loadFSImage(startOpt);
        } catch (IOException ioe) {
            // ...
        }
        // ...
    }
}
```

### 1.3 初始化 NameNode 的 RPC 服务端

```java
@InterfaceAudience.Private
public class NameNode extends ReconfigurableBase implements
    NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {
    protected void initialize(Configuration conf) throws IOException {
        // ...
        loadNamesystem(conf);
        // ...
        rpcServer = createRpcServer(conf);
        // ...
    }
    
    protected NameNodeRpcServer createRpcServer(Configuration conf) 
        throws IOException {
        return new NameNodeRpcServer(conf, this);
    }
}

@InterfaceAudience.Private
@VisibleForTesting
public class NameNodeRpcServer implements NamenodeProtocols {
    public NameNodeRpcServer(Configuration conf, NameNode nn) throws IOException {
        // ...
        if (serviceRpcAddr != null) {
            serviceRpcServer = new RPC.Builder(conf)
                .setProtocol(
                org.apache.hadoop.hdfs.protocolPB.ClientNamenodeProtocolPB.class)
                .setInstance(clientNNPbService)
                .setBindAddress(bindHost)
                .setPort(serviceRpcAddr.getPort())
                .setNumHandlers(serviceHandlerCount)
                .setVerbose(false)
                .setSecretManager(namesystem.getDelegationTokenSecretManager())
                .build();
        } else {
            // ,,,
        }
        // ...
    }
}
```

### 1.4 NameNode 启动资源检查

```java
@InterfaceAudience.Private
public class NameNode extends ReconfigurableBase implements
    NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {
    protected void initialize(Configuration conf) throws IOException {
        // ...
        rpcServer = createRpcServer(conf);
        // ...
        startCommonServices(conf);
        startMetricsLogger(conf);
    }
    
    private void startCommonServices(Configuration conf) throws IOException {
        namesystem.startCommonServices(conf, haContext);
        // ...
    } 
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements Namesystem, FSNamesystemMBean,
    NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
	void startCommonServices(Configuration conf, HAContext haContext) throws IOException {
        // ...
        try {
            nnResourceChecker = new NameNodeResourceChecker(conf);
            checkAvailableResources();
            // ..,
        } finally {
      		writeUnlock("startCommonServices");
    	}
        // ...
    }
    
	void checkAvailableResources() {
        long resourceCheckTime = monotonicNow();
        Preconditions.checkState(nnResourceChecker != null, 
                                 "nnResourceChecker not initialized");
        hasResourcesAvailable = nnResourceChecker.hasAvailableDiskSpace();
        resourceCheckTime = monotonicNow() - resourceCheckTime;
        NameNode.getNameNodeMetrics().addResourceCheckTime(resourceCheckTime);
    }
}

@InterfaceAudience.Private
public class NameNodeResourceChecker {
    public NameNodeResourceChecker(Configuration conf) throws IOException {
        this.conf = conf;
        volumes = new HashMap<String, CheckedVolume>();
        duReserved = conf.getLongBytes(DFSConfigKeys.DFS_NAMENODE_DU_RESERVED_KEY,
                                       DFSConfigKeys.DFS_NAMENODE_DU_RESERVED_DEFAULT);
        
    }
    
    public boolean hasAvailableDiskSpace() {
        return NameNodeResourcePolicy
            .areResourcesAvailable(volumes.values(),
                                   minimumRedundantVolumes);
    }
}

@InterfaceAudience.Private
public class DFSConfigKeys extends CommonConfigurationKeys {
    public static final String  DFS_NAMENODE_DU_RESERVED_KEY = "dfs.namenode.resource.du.reserved";
    public static final long    DFS_NAMENODE_DU_RESERVED_DEFAULT = 1024 * 1024 * 100; // 100 MB
}

@InterfaceAudience.Private
final class NameNodeResourcePolicy {
    static boolean areResourcesAvailable(
      Collection<? extends CheckableNameNodeResource> resources,
      int minimumRedundantResources) {
        // ...
        for (CheckableNameNodeResource resource : resources) {
            if (!resource.isRequired()) {
                // ...
            } else {
                requiredResourceCount++;
                if (!resource.isResourceAvailable()) { // Interface CheckableNameNodeResource {}
                    return false;
                }
            }
        }
    }
}

@InterfaceAudience.Private
public class NameNodeResourceChecker {
    @VisibleForTesting
    class CheckedVolume implements CheckableNameNodeResource {
        @Override
        public boolean isResourceAvailable() {
            long availableSpace = df.getAvailable();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Space available on volume '" + volume + "' is "
                          + availableSpace);
            }
            if (availableSpace < duReserved) {
                LOG.warn("Space available on volume '" + volume + "' is "
                         + availableSpace +
                         ", which is below the configured reserved amount " + duReserved);
                return false;
            } else {
                return true;
            }
        }
    }
}
```

### 1.5 NameNode 对心跳的超时判断

```java
@InterfaceAudience.Private
public class NameNode extends ReconfigurableBase implements
    NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {
    protected void initialize(Configuration conf) throws IOException {
        // ...
        startCommonServices(conf);
        startMetricsLogger(conf);
    }
    
    private void startCommonServices(Configuration conf) throws IOException {
        namesystem.startCommonServices(conf, haContext);
        // ...
    }
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements Namesystem, FSNamesystemMBean,
    NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
	void startCommonServices(Configuration conf, HAContext haContext) throws IOException {
        // ...
        try {
            nnResourceChecker = new NameNodeResourceChecker(conf);
            // ...
            blockManager.activate(conf, completeBlocksTotal);
        } finally {
            writeUnlock("startCommonServices");
        }
        // ...
    }
}

@InterfaceAudience.Private
public class BlockManager implements BlockStatsMXBean {
    public void activate(Configuration conf, long blockTotal) {
        pendingReconstruction.start();
        datanodeManager.activate(conf);
        // ...
    }
}

@InterfaceAudience.Private
@InterfaceStability.Evolving
public class DatanodeManager {
    private long heartbeatExpireInterval;
    private volatile long heartbeatIntervalSeconds;
    private volatile int heartbeatRecheckInterval;
    
    DatanodeManager(final BlockManager blockManager, final Namesystem namesystem,
      final Configuration conf) throws IOException {
        heartbeatIntervalSeconds = conf.getTimeDuration(
            DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY,
            DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT, TimeUnit.SECONDS);
        heartbeatRecheckInterval = conf.getInt(
            DFSConfigKeys.DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_KEY, 
            DFSConfigKeys.DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_DEFAULT);
        this.heartbeatExpireInterval = 
            2 * heartbeatRecheckInterval + 10 * 1000 * heartbeatIntervalSeconds;
    }
    
    void activate(final Configuration conf) {
        datanodeAdminManager.activate(conf);
        heartbeatManager.activate();
    }
    
    boolean isDatanodeDead(DatanodeDescriptor node) {
        return (node.getLastUpdateMonotonic() <
                (monotonicNow() - heartbeatExpireInterval));
    }
}

class HeartbeatManager implements DatanodeStatistics {
    private final Daemon heartbeatThread = new Daemon(new Monitor());
    
    void activate() {
        heartbeatThread.start();
    }
    
    private class Monitor implements Runnable {
        @Override
        public void run() {
            while(namesystem.isRunning()) {
                restartHeartbeatStopWatch();
                try {
                    final long now = Time.monotonicNow();
                    if (lastHeartbeatCheck + heartbeatRecheckInterval < now) {
                        heartbeatCheck();
                        lastHeartbeatCheck = now;
                    }
                    if (blockManager.shouldUpdateBlockKey(now - lastBlockKeyUpdate)) {
                        synchronized(HeartbeatManager.this) {
                            for(DatanodeDescriptor d : datanodes) {
                                d.setNeedKeyUpdate(true);
                            }
                        }
                        lastBlockKeyUpdate = now;
                    }
                } catch (Exception e) {
                    LOG.error("Exception while checking heartbeat", e);
                }
                try {
                    Thread.sleep(5000);  // 5 seconds
                } catch (InterruptedException ignored) {
                }
                // avoid declaring nodes dead for another cycle if a GC pause lasts
                // longer than the node recheck interval
                if (shouldAbortHeartbeatCheck(-5000)) {
                    LOG.warn("Skipping next heartbeat scan due to excessive pause");
                    lastHeartbeatCheck = Time.monotonicNow();
                }
            }
        }
    }
    
    @VisibleForTesting
    void heartbeatCheck() {
        // ...
        while (!allAlive) {
            synchronized(this) {
                for (DatanodeDescriptor d : datanodes) {
					// ...
                    if (dead == null && dm.isDatanodeDead(d)) {
                        stats.incrExpiredHeartbeats();
                        dead = d;
                        removeNodeFromStaleList(d);
                    }
                }
            }
        }
    }
}

public class DFSConfigKeys extends CommonConfigurationKeys {
    public static final String  DFS_HEARTBEAT_INTERVAL_KEY = "dfs.heartbeat.interval";
    public static final long    DFS_HEARTBEAT_INTERVAL_DEFAULT = 3;
    public static final String  DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_KEY = 
        HdfsClientConfigKeys.DeprecatedKeys.DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_KEY;
    public static final int     DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_DEFAULT = 5*60*1000;
}

@InterfaceAudience.Private
public interface HdfsClientConfigKeys {
    interface DeprecatedKeys {
        String DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_KEY = 
            "dfs.namenode.heartbeat.recheck-interval";
    }
}
```

### 1.6 安全模式

```java
@InterfaceAudience.Private
public class NameNode extends ReconfigurableBase implements
    NameNodeStatusMXBean, TokenVerifier<DelegationTokenIdentifier> {
    protected void initialize(Configuration conf) throws IOException {
        // ...
        startCommonServices(conf);
        startMetricsLogger(conf);
    }
    
    private void startCommonServices(Configuration conf) throws IOException {
        namesystem.startCommonServices(conf, haContext);
        // ...
    }
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements Namesystem, FSNamesystemMBean,
    NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
	void startCommonServices(Configuration conf, HAContext haContext) throws IOException {
        // ...
        try {
            // ...
            blockManager.activate(conf, completeBlocksTotal);
        } finally {
            writeUnlock("startCommonServices");
        }
        // ...
    }
}

@InterfaceAudience.Private
public class BlockManager implements BlockStatsMXBean {
    public void activate(Configuration conf, long blockTotal) {
        pendingReconstruction.start();
        datanodeManager.activate(conf);
        // ...
        bmSafeMode.activate(blockTotal);
    }
}

@InterfaceAudience.Private
@InterfaceStability.Evolving
class BlockManagerSafeMode {
    void activate(long total) {
        assert namesystem.hasWriteLock();
        assert status == BMSafeModeStatus.OFF;

        startTime = monotonicNow();
        setBlockTotal(total);
        if (areThresholdsMet()) {
            boolean exitResult = leaveSafeMode(false);
            Preconditions.checkState(exitResult, "Failed to leave safe mode.");
        } else {
            // enter safe mode
            status = BMSafeModeStatus.PENDING_THRESHOLD;
            initializeReplQueuesIfNecessary();
            reportStatus("STATE* Safe mode ON.", true);
            lastStatusReport = monotonicNow();
        }
    }
    
    private final float threshold;
    private final float replQueueThreshold;
    
    BlockManagerSafeMode(BlockManager blockManager, Namesystem namesystem,
      boolean haEnabled, Configuration conf) {
        this.threshold = conf.getFloat(DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_KEY,
                                       DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_DEFAULT);
        this.replQueueThreshold = 
            conf.getFloat(DFS_NAMENODE_REPL_QUEUE_THRESHOLD_PCT_KEY, threshold);
    }
    
    void setBlockTotal(long total) {
        assert namesystem.hasWriteLock();
        synchronized (this) {
            this.blockTotal = total;
            this.blockThreshold = (long) (total * threshold);
        }
        this.blockReplQueueThreshold = (long) (total * replQueueThreshold);
    }
    
    private boolean areThresholdsMet() {
        assert namesystem.hasWriteLock();
        // Calculating the number of live datanodes is time-consuming
        // in large clusters. Skip it when datanodeThreshold is zero.
        // We need to evaluate getNumLiveDataNodes only when
        // (blockSafe >= blockThreshold) is true and hence moving evaluation
        // of datanodeNum conditional to isBlockThresholdMet as well
        synchronized (this) {
            boolean isBlockThresholdMet = (blockSafe >= blockThreshold);
            boolean isDatanodeThresholdMet = true;
            if (isBlockThresholdMet && datanodeThreshold > 0) {
                int datanodeNum = blockManager.getDatanodeManager().
                    getNumLiveDataNodes();
                isDatanodeThresholdMet = (datanodeNum >= datanodeThreshold);
            }
            return isBlockThresholdMet && isDatanodeThresholdMet;
        }
    }
}

public class DFSConfigKeys extends CommonConfigurationKeys {
    public static final String  DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_KEY =
        HdfsClientConfigKeys.DeprecatedKeys.DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_KEY;
    public static final float   DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_DEFAULT = 0.999f;
}

@InterfaceAudience.Private
public interface HdfsClientConfigKeys {
    interface DeprecatedKeys {
        String DFS_NAMENODE_SAFEMODE_THRESHOLD_PCT_KEY =
            "dfs.namenode.safemode.threshold-pct";
    }
}
```

## 2. DataNode 启动源码解析

- DataNode 工作机制

    1. DataNode 启动后向 NameNode 注册
    2. DataNode 注册成功
    3. 每周期（6 小时）上报所有块信息
    4. 每 3 秒一次心跳，返回结果带有 NameNode 给 DataNode 的命令
    5. 超过 10 分钟 + 30 秒没有收到心跳，则认为该节点不可用

- DataNode 位置

    ```java
    package org.apache.hadoop.hdfs.server.datanode;
    
    @InterfaceAudience.Private
    public class DataNode extends ReconfigurableBase
        implements InterDatanodeProtocol, ClientDatanodeProtocol,
            TraceAdminProtocol, DataNodeMXBean, ReconfigurationProtocol {}
    ```

### 2.1 初始化 DataXceivierServer

```java
@InterfaceAudience.Private
public class DataNode extends ReconfigurableBase
    implements InterDatanodeProtocol, ClientDatanodeProtocol, 
TraceAdminProtocol, DataNodeMXBean, ReconfigurationProtocol {
    public static void main(String args[]) {
        if (DFSUtil.parseHelpArgument(args, DataNode.USAGE, System.out, true)) {
            System.exit(0);
        }
        secureMain(args, null);
    }

    public static void secureMain(String args[], SecureResources resources) {
        int errorCode = 0;
        try {
            StringUtils.startupShutdownMessage(DataNode.class, args, LOG);
            DataNode datanode = createDataNode(args, null, resources);
            // ...
        } catch (Throwable e) {
            LOG.error("Exception in secureMain", e);
            terminate(1, e);
        } finally {
            // ...
        }
    }

    @VisibleForTesting
    @InterfaceAudience.Private
    public static DataNode createDataNode(String args[], Configuration conf, 
                                          SecureResources resources) throws IOException {
        DataNode dn = instantiateDataNode(args, conf, resources);
        if (dn != null) {
            dn.runDatanodeDaemon();
        }
        return dn;
    }
    
    public static DataNode instantiateDataNode(String args [], Configuration conf, 
                                               SecureResources resources) throws IOException {
        // ...
        return makeInstance(dataLocations, conf, resources);
    }
    
    static DataNode makeInstance(Collection<StorageLocation> dataDirs, 
                                 Configuration conf, SecureResources resources) throws IOException {
        // ...
        return new DataNode(conf, locations, storageLocationChecker, resources);
    }
    
    DataNode(final Configuration conf,
           final List<StorageLocation> dataDirs,
           final StorageLocationChecker storageLocationChecker,
           final SecureResources resources) throws IOException {
        // ...
        try {
            hostName = getHostName(conf);
            LOG.info("Configured hostname is {}", hostName);
            startDataNode(dataDirs, resources);
        } catch (IOException ie) {
            shutdown();
            throw ie;
        }
        // ...
    }
    
    void startDataNode(List<StorageLocation> dataDirectories, SecureResources resources) throws IOException {
        // ...
        initDataXceiver();
        // ...
    }
    
    private void initDataXceiver() throws IOException {
        // 上传 Daemon
    }
}
```

### 2.2 初始化 HTTP 服务

```java
@InterfaceAudience.Private
public class DataNode extends ReconfigurableBase
    implements InterDatanodeProtocol, ClientDatanodeProtocol, 
TraceAdminProtocol, DataNodeMXBean, ReconfigurationProtocol {
    void startDataNode(List<StorageLocation> dataDirectories, SecureResources resources) throws IOException {
        // ...
        initDataXceiver();
        startInfoServer();
        // ...
    }
    
    private void startInfoServer() throws IOException {
        ServerSocketChannel httpServerChannel = secureResources != null ?
            secureResources.getHttpServerChannel() : null;

        httpServer = new DatanodeHttpServer(getConf(), this, httpServerChannel);
        httpServer.start();
        if (httpServer.getHttpAddress() != null) {
            infoPort = httpServer.getHttpAddress().getPort();
        }
        if (httpServer.getHttpsAddress() != null) {
            infoSecurePort = httpServer.getHttpsAddress().getPort();
        }
    }
}

public class DatanodeHttpServer implements Closeable {
    public DatanodeHttpServer(final Configuration conf,
                              final DataNode datanode,
                              final ServerSocketChannel externalHttpChannel) throws IOException {
        // ...
        HttpServer2.Builder builder = new HttpServer2.Builder()
            .setName("datanode")
            .setConf(confForInfoServer)
            .setACL(new AccessControlList(conf.get(DFS_ADMIN, " ")))
            .hostName(getHostnameForSpnegoPrincipal(confForInfoServer))
            .addEndpoint(URI.create("http://localhost:" + proxyPort))
            .setFindPort(true);
        // ...
    }
}
```

### 2.3 初始化 DataNode 的 RPC 服务器

```java
@InterfaceAudience.Private
public class DataNode extends ReconfigurableBase
    implements InterDatanodeProtocol, ClientDatanodeProtocol, 
TraceAdminProtocol, DataNodeMXBean, ReconfigurationProtocol {
    void startDataNode(List<StorageLocation> dataDirectories, SecureResources resources) throws IOException {
        // ...
        startInfoServer();
        // ...
        initIpcServer();
    }
    
    public RPC.Server ipcServer;
    
    private void initIpcServer() throws IOException {
        // ...
        ipcServer = new RPC.Builder(getConf())
            .setProtocol(ClientDatanodeProtocolPB.class)
            .setInstance(service)
            .setBindAddress(ipcAddr.getHostName())
            .setPort(ipcAddr.getPort())
            .setNumHandlers(
            getConf().getInt(DFS_DATANODE_HANDLER_COUNT_KEY,
                             DFS_DATANODE_HANDLER_COUNT_DEFAULT)).setVerbose(false)
            .setSecretManager(blockPoolTokenSecretManager).build();
        // ...
    }
}
```

### 2.4 DataNode 向 NameNode 注册

```java
@InterfaceAudience.Private
public class DataNode extends ReconfigurableBase
    implements InterDatanodeProtocol, ClientDatanodeProtocol, 
TraceAdminProtocol, DataNodeMXBean, ReconfigurationProtocol {
    void startDataNode(List<StorageLocation> dataDirectories, SecureResources resources) throws IOException {
        // ...
        initIpcServer();
        // ...
        blockPoolManager.refreshNamenodes(getConf());
        // ...
    }
    
    DatanodeProtocolClientSideTranslatorPB connectToNN(
        InetSocketAddress nnAddr) throws IOException {
        return new DatanodeProtocolClientSideTranslatorPB(nnAddr, getConf());
    }
}

@InterfaceAudience.Private
class BlockPoolManager {
    void refreshNamenodes(Configuration conf) throws IOException {
        // ...
        synchronized (refreshNamenodesLock) {
            doRefreshNamenodes(newAddressMap, newLifelineAddressMap);
        }
    }
    
    private void doRefreshNamenodes(
        Map<String, Map<String, InetSocketAddress>> addrMap,
        Map<String, Map<String, InetSocketAddress>> lifelineAddrMap)
        throws IOException {
        // ...
		synchronized (this) {
            // ...
            if (!toAdd.isEmpty()) {
                // ...
                for (String nsToAdd : toAdd) {
                    // ...
                    BPOfferService bpos = createBPOS(nsToAdd, nnIds, addrs, lifelineAddrs);
                    // ...
                }
                // ...
            }
            startAll();
        }
    }
    
    protected BPOfferService createBPOS(
        final String nameserviceId,
        List<String> nnIds,
        List<InetSocketAddress> nnAddrs,
        List<InetSocketAddress> lifelineNnAddrs) {
        return new BPOfferService(nameserviceId, nnIds, nnAddrs, lifelineNnAddrs, dn);
    }
    
    synchronized void startAll() throws IOException {
        try {
            UserGroupInformation.getLoginUser().doAs(
                new PrivilegedExceptionAction<Object>() {
                    @Override
                    public Object run() throws Exception {
                        for (BPOfferService bpos : offerServices) {
                            bpos.start();
                        }
                        return null;
                    }
                });
        } catch (InterruptedException ex) {
            IOException ioe = new IOException();
            ioe.initCause(ex.getCause());
            throw ioe;
        }
    }
}

@InterfaceAudience.Private
class BPOfferService {
    BPOfferService(
        final String nameserviceId, List<String> nnIds,
        List<InetSocketAddress> nnAddrs,
        List<InetSocketAddress> lifelineNnAddrs,
        DataNode dn) {
		// ...
        for (int i = 0; i < nnAddrs.size(); ++i) {
            this.bpServices.add(new BPServiceActor(nameserviceId, nnIds.get(i),
                                                   nnAddrs.get(i), lifelineNnAddrs.get(i), this));
        }
    }
    
    void start() {
        for (BPServiceActor actor : bpServices) {
            actor.start();
        }
    }
}

@InterfaceAudience.Private
class BPServiceActor implements Runnable {
    void start() {
        if ((bpThread != null) && (bpThread.isAlive())) {
            //Thread is started already
            return;
        }
        bpThread = new Thread(this);
        bpThread.setDaemon(true); // needed for JUnit testing

        if (lifelineSender != null) {
            lifelineSender.start();
        }
        bpThread.start();
    }
    
    @Override
    public void run() {
		// ...
        try {
            while (true) {
                try {
                    connectToNNAndHandshake();
                    break;
                } catch (IOException ioe) {
					// ...                    
                }
            }
            // ...
        }catch (Throwable ex) {
            LOG.warn("Unexpected exception in block pool " + this, ex);
            runningState = RunningState.FAILED;
        } finally {
            LOG.warn("Ending block pool service for: " + this);
            cleanUp();
        }
    }
    
    private void connectToNNAndHandshake() throws IOException {
        // get NN proxy
        bpNamenode = dn.connectToNN(nnAddr); // method in DataNode class
        // ...
        register(nsInfo);
    }
    
    void register(NamespaceInfo nsInfo) throws IOException {
        while (shouldRun()) {
            try {
                // Use returned registration from namenode with updated fields
                newBpRegistration = bpNamenode.registerDatanode(newBpRegistration);
                newBpRegistration.setNamespaceInfo(nsInfo);
                bpRegistration = newBpRegistration;
                break;
            } catch(Exception e) {
                // ...
            }
            sleepAndLogInterrupts(1000, "connecting to server");
        }
    }
}

@InterfaceAudience.Private
@InterfaceStability.Stable
public class DatanodeProtocolClientSideTranslatorPB implements
    ProtocolMetaInterface, DatanodeProtocol, Closeable {
    public DatanodeProtocolClientSideTranslatorPB(InetSocketAddress nameNodeAddr,
                                                  Configuration conf) throws IOException {
        RPC.setProtocolEngine(conf, DatanodeProtocolPB.class, ProtobufRpcEngine2.class);
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        rpcProxy = createNamenode(nameNodeAddr, conf, ugi);
    }
    
    private static DatanodeProtocolPB createNamenode(
        InetSocketAddress nameNodeAddr, Configuration conf,
        UserGroupInformation ugi) throws IOException {
        return RPC.getProxy(DatanodeProtocolPB.class,
                            RPC.getProtocolVersion(DatanodeProtocolPB.class), 
                            nameNodeAddr, 
                            ugi,
                            conf, 
                            NetUtils.getSocketFactory(conf, DatanodeProtocolPB.class));
    }
    
    @Override
    public DatanodeRegistration registerDatanode(DatanodeRegistration registration) throws IOException {
        // ...
        try {
            resp = rpcProxy.registerDatanode(NULL_CONTROLLER, builder.build());
        } catch (ServiceException se) {
            throw ProtobufHelper.getRemoteException(se);
        }
        // ...
    }
}

@InterfaceAudience.Private
@VisibleForTesting
public class NameNodeRpcServer implements NamenodeProtocols {
    @Override // DatanodeProtocol
    public DatanodeRegistration registerDatanode(DatanodeRegistration nodeReg)
        throws IOException {
        checkNNStartup();
        verifySoftwareVersion(nodeReg);
        namesystem.registerDatanode(nodeReg);
        return nodeReg;
    }
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements 
    Namesystem, FSNamesystemMBean, NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
    void registerDatanode(DatanodeRegistration nodeReg) throws IOException {
        writeLock();
        try {
            blockManager.registerDatanode(nodeReg);
        } finally {
            writeUnlock("registerDatanode");
        }
    }
}

@InterfaceAudience.Private
public class BlockManager implements BlockStatsMXBean {
    public void registerDatanode(DatanodeRegistration nodeReg)
        throws IOException {
        assert namesystem.hasWriteLock();
        datanodeManager.registerDatanode(nodeReg);
        bmSafeMode.checkSafeMode();
    }
}

@InterfaceAudience.Private
@InterfaceStability.Evolving
public class DatanodeManager {
    public void registerDatanode(DatanodeRegistration nodeReg)
      throws DisallowedDatanodeException, UnresolvedTopologyException {
        // ...
        try {
            // ...
            try {
                // ...
                addDatanode(nodeDescr);
                blockManager.getBlockReportLeaseManager().register(nodeDescr);
                // also treat the registration message as a heartbeat
                // no need to update its timestamp
                // because its is done when the descriptor is created
                heartbeatManager.addDatanode(nodeDescr);
                heartbeatManager.updateDnStat(nodeDescr);
            } finally {
                // ...
            }
        } catch (InvalidTopologyException e) {
            // ...
            throw e;
        }
    }
}
```

### 2.5 向 NameNode 发送心跳

```java
@InterfaceAudience.Private
public class DataNode extends ReconfigurableBase
    implements InterDatanodeProtocol, ClientDatanodeProtocol, 
TraceAdminProtocol, DataNodeMXBean, ReconfigurationProtocol {
    void startDataNode(List<StorageLocation> dataDirectories, SecureResources resources) throws IOException {
        // ...
        blockPoolManager.refreshNamenodes(getConf());
        // ...
    }
}

@InterfaceAudience.Private
class BlockPoolManager {
    void refreshNamenodes(Configuration conf) throws IOException {
        // ...
        synchronized (refreshNamenodesLock) {
            doRefreshNamenodes(newAddressMap, newLifelineAddressMap);
        }
    }
    
    private void doRefreshNamenodes(
        Map<String, Map<String, InetSocketAddress>> addrMap,
        Map<String, Map<String, InetSocketAddress>> lifelineAddrMap)
        throws IOException {
        // ...
		synchronized (this) {
            // ...
            startAll();
        }
    }
    
    synchronized void startAll() throws IOException {
        try {
            UserGroupInformation.getLoginUser().doAs(
                new PrivilegedExceptionAction<Object>() {
                    @Override
                    public Object run() throws Exception {
                        for (BPOfferService bpos : offerServices) {
                            bpos.start();
                        }
                        return null;
                    }
                });
        } catch (InterruptedException ex) {
            IOException ioe = new IOException();
            ioe.initCause(ex.getCause());
            throw ioe;
        }
    }
}

@InterfaceAudience.Private
class BPOfferService {
    BPOfferService(
        final String nameserviceId, List<String> nnIds,
        List<InetSocketAddress> nnAddrs,
        List<InetSocketAddress> lifelineNnAddrs,
        DataNode dn) {
		// ...
        for (int i = 0; i < nnAddrs.size(); ++i) {
            this.bpServices.add(new BPServiceActor(nameserviceId, nnIds.get(i),
                                                   nnAddrs.get(i), lifelineNnAddrs.get(i), this));
        }
    }
    
    void start() {
        for (BPServiceActor actor : bpServices) {
            actor.start();
        }
    }
}

@InterfaceAudience.Private
class BPServiceActor implements Runnable {
    void start() {
        if ((bpThread != null) && (bpThread.isAlive())) {
            //Thread is started already
            return;
        }
        bpThread = new Thread(this);
        bpThread.setDaemon(true); // needed for JUnit testing

        if (lifelineSender != null) {
            lifelineSender.start();
        }
        bpThread.start();
    }
    
    @Override
    public void run() {
		// ...
        try {
            while (true) {
                try {
                    connectToNNAndHandshake();
                    break;
                } catch (IOException ioe) {
					// ...                    
                }
            }
            // ...
            while (shouldRun()) {
                try {
                    offerService();
                } catch (Exception ex) {
                    LOG.error("Exception in BPOfferService for " + this, ex);
                    sleepAndLogInterrupts(5000, "offering service");
                }
            }
            runningState = RunningState.EXITED;
        }catch (Throwable ex) {
            LOG.warn("Unexpected exception in block pool " + this, ex);
            runningState = RunningState.FAILED;
        } finally {
            LOG.warn("Ending block pool service for: " + this);
            cleanUp();
        }
    }
    
    private void offerService() throws Exception {
        while (shouldRun()) {
            try {
                // ...
                if (sendHeartbeat) {
                    if (!dn.areHeartbeatsDisabledForTests()) {
                        resp = sendHeartBeat(requestBlockReportLease);
                        // ...
                    }
                    // ...
                }
                // ...
            } catch (Exception e) {
                // ...
            } finally {
                // ...
            }
        }
    }
    
    HeartbeatResponse sendHeartBeat(boolean requestBlockReportLease) throws IOException {
        // ...
        HeartbeatResponse response = bpNamenode.sendHeartbeat(bpRegistration,
                                                              reports,
                                                              dn.getFSDataset().getCacheCapacity(),
                                                              dn.getFSDataset().getCacheUsed(),
                                                              dn.getXmitsInProgress(),
                                                              dn.getActiveTransferThreadCount(),
                                                              numFailedVolumes,
                                                              volumeFailureSummary,
                                                              requestBlockReportLease,
                                                              slowPeers,
                                                              slowDisks);
    }
}

@InterfaceAudience.Private
@VisibleForTesting
public class NameNodeRpcServer implements NamenodeProtocols {
    @Override // DatanodeProtocol
    public HeartbeatResponse sendHeartbeat(DatanodeRegistration nodeReg,
                                           StorageReport[] report, long dnCacheCapacity, long dnCacheUsed,
                                           int xmitsInProgress, int xceiverCount,
                                           int failedVolumes, VolumeFailureSummary volumeFailureSummary,
                                           boolean requestFullBlockReportLease,
                                           @Nonnull SlowPeerReports slowPeers,
                                           @Nonnull SlowDiskReports slowDisks)
        throws IOException {
        checkNNStartup();
        verifyRequest(nodeReg);
        return namesystem.handleHeartbeat(nodeReg, report,
                                          dnCacheCapacity, dnCacheUsed, xceiverCount, xmitsInProgress,
                                          failedVolumes, volumeFailureSummary, requestFullBlockReportLease,
                                          slowPeers, slowDisks);
    }
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements 
    Namesystem, FSNamesystemMBean, NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
    HeartbeatResponse handleHeartbeat(DatanodeRegistration nodeReg,
                                      StorageReport[] reports, long cacheCapacity, long cacheUsed,
                                      int xceiverCount, int xmitsInProgress, int failedVolumes,
                                      VolumeFailureSummary volumeFailureSummary,
                                      boolean requestFullBlockReportLease,
                                      @Nonnull SlowPeerReports slowPeers,
                                      @Nonnull SlowDiskReports slowDisks)
        throws IOException {
        readLock();
        try {
			// ...
            DatanodeCommand[] cmds = blockManager.getDatanodeManager().handleHeartbeat(
                nodeReg, reports, getBlockPoolId(), cacheCapacity, cacheUsed,
                xceiverCount, maxTransfer, failedVolumes, volumeFailureSummary,
                slowPeers, slowDisks);
            // ...
            return new HeartbeatResponse(cmds, haState, rollingUpgradeInfo, blockReportLeaseId);
        } finally {
            readUnlock("handleHeartbeat");
        }
    }
}

@InterfaceAudience.Private
@InterfaceStability.Evolving
public class DatanodeManager {
    public DatanodeCommand[] handleHeartbeat(DatanodeRegistration nodeReg,
                                             StorageReport[] reports, final String blockPoolId,
                                             long cacheCapacity, long cacheUsed, int xceiverCount, 
                                             int maxTransfers, int failedVolumes,
                                             VolumeFailureSummary volumeFailureSummary,
                                             @Nonnull SlowPeerReports slowPeers,
                                             @Nonnull SlowDiskReports slowDisks) throws IOException {
        // ...
        heartbeatManager.updateHeartbeat(nodeinfo, reports, cacheCapacity,
                                         cacheUsed, xceiverCount, failedVolumes, volumeFailureSummary);
        // ...
    }
}

class HeartbeatManager implements DatanodeStatistics {
    synchronized void updateHeartbeat(final DatanodeDescriptor node,
                                      StorageReport[] reports, long cacheCapacity, long cacheUsed,
                                      int xceiverCount, int failedVolumes,
                                      VolumeFailureSummary volumeFailureSummary) {
        stats.subtract(node);
        blockManager.updateHeartbeat(node, reports, cacheCapacity, cacheUsed,
                                     xceiverCount, failedVolumes, volumeFailureSummary);
        stats.add(node);
    }
}

@InterfaceAudience.Private
public class BlockManager implements BlockStatsMXBean {
    void updateHeartbeat(DatanodeDescriptor node, StorageReport[] reports,
                         long cacheCapacity, long cacheUsed, int xceiverCount, int failedVolumes,
                         VolumeFailureSummary volumeFailureSummary) {
        for (StorageReport report: reports) {
            providedStorageMap.updateStorage(node, report.getStorage());
        }
        node.updateHeartbeat(reports, cacheCapacity, cacheUsed, xceiverCount,
                             failedVolumes, volumeFailureSummary);
    }
}

@InterfaceAudience.Private
@InterfaceStability.Evolving
public class DatanodeDescriptor extends DatanodeInfo {
    void updateHeartbeat(StorageReport[] reports, long cacheCapacity,
                         long cacheUsed, int xceiverCount, int volFailures,
                         VolumeFailureSummary volumeFailureSummary) {
        updateHeartbeatState(reports, cacheCapacity, cacheUsed, xceiverCount,
                             volFailures, volumeFailureSummary);
        heartbeatedSinceRegistration = true;
    }
    
    void updateHeartbeatState(StorageReport[] reports, long cacheCapacity,
                              long cacheUsed, int xceiverCount, int volFailures,
                              VolumeFailureSummary volumeFailureSummary) {
        updateStorageStats(reports, cacheCapacity, cacheUsed, xceiverCount,
                           volFailures, volumeFailureSummary);
        setLastUpdate(Time.now());
        setLastUpdateMonotonic(Time.monotonicNow());
        rollBlocksScheduled(getLastUpdateMonotonic());
    }
}
```

## 3. HDFS 启动源码解析

- HDFS 写数据流程
    1. 向 NameNode 请求上传文件
    2. 响应可以上传文件
    3. 请求上传第一个 Block，请求返回 DataNode
    4. 返回 DataNode 节点
    5. 请求建立 Block 传输通道（FSDataOutputStream）
    6. DataNode 应答成功
    7. 传输数据 packet（64k）

### 3.1 Create

#### 3.1.1 DN 向 NN 发起创建请求以及 NN 处理 DN 的创建请求

```java
public class Demo {
    private static FileSystem fs = null;
    
    static {
        try {
            URI uri = new URI("hdfs://hadoop01:8020");

            Configuration cfg = new Configuration();
            String user = "root";

            fs = FileSystem.get(uri, cfg, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Init Error");
        }
    }
        
    public static void main(String[] args) {
		FSDataOutputStream fos = fs.create(new Path("/input"));
        fos.write("hello world".getBytes());
    }   
}
```

hadoop-hdfs-client

```java
package org.apache.hadoop.hdfs;

@InterfaceAudience.LimitedPrivate({ "MapReduce", "HBase" })
@InterfaceStability.Unstable
public class DistributedFileSystem extends FileSystem
    implements KeyProviderTokenIssuer, BatchListingOperations {
    @Override
    public FSDataOutputStream create(final Path f, final FsPermission permission,
                                     final EnumSet<CreateFlag> cflags, final int bufferSize,
                                     final short replication, final long blockSize,
                                     final Progressable progress, final ChecksumOpt checksumOpt)
        throws IOException {
		// ...
        return new FileSystemLinkResolver<FSDataOutputStream>() {
            @Override
            public FSDataOutputStream doCall(final Path p) throws IOException {
                final DFSOutputStream dfsos = dfs.create(getPathName(p), permission,
                                                         cflags, replication, blockSize, progress, bufferSize,
                                                         checksumOpt);
                return safelyCreateWrappedOutputStream(dfsos);
            }
            @Override
            public FSDataOutputStream next(final FileSystem fs, final Path p)
                throws IOException {
                return fs.create(p, permission, cflags, bufferSize,
                                 replication, blockSize, progress, checksumOpt);
            }
        }.resolve(this, absF);
    }
}

@InterfaceAudience.Private
public class DFSClient implements 
    java.io.Closeable, RemotePeerFactory, DataEncryptionKeyFactory, KeyProviderTokenIssuer {
    public DFSOutputStream create(String src, FsPermission permission,
                                  EnumSet<CreateFlag> flag, boolean createParent, short replication,
                                  long blockSize, Progressable progress, int buffersize,
                                  ChecksumOpt checksumOpt, InetSocketAddress[] favoredNodes,
                                  String ecPolicyName, String storagePolicy)
        throws IOException {
		// ...
        final DFSOutputStream result = 
            DFSOutputStream.newStreamForCreate(this, 
                                               src, 
                                               masked, 
                                               flag, 
                                               createParent, 
                                               replication, 
                                               blockSize, 
                                               progress,
                                               dfsClientConf.createChecksum(checksumOpt),
                                               getFavoredNodesStr(favoredNodes), 
                                               ecPolicyName, 
                                               storagePolicy);
        // ...
    }
}

@InterfaceAudience.Private
public class DFSOutputStream extends FSOutputSummer 
    implements Syncable, CanSetDropBehind, StreamCapabilities {
    static DFSOutputStream newStreamForCreate(DFSClient dfsClient, String src,
                                              FsPermission masked, EnumSet<CreateFlag> flag, boolean createParent,
                                              short replication, long blockSize, Progressable progress,
                                              DataChecksum checksum, String[] favoredNodes, String ecPolicyName,
                                              String storagePolicy)
        throws IOException {
        try (TraceScope ignored =
             dfsClient.newPathTraceScope("newStreamForCreate", src)) {
            // ...
            while (shouldRetry) {
                try {
                    stat = dfsClient.namenode.create(src, masked, dfsClient.clientName,
                                                     new EnumSetWritable<>(flag), createParent, replication,
                                                     blockSize, SUPPORTED_CRYPTO_VERSIONS, ecPolicyName,
                                                     storagePolicy);
                } catch (RemoteException re) {
                    // ...
                }
            }
        }
    }
}

@InterfaceAudience.Private
@VisibleForTesting
public class NameNodeRpcServer implements NamenodeProtocols {
    @Override // ClientProtocol
    public HdfsFileStatus create(String src, FsPermission masked,
                                 String clientName, EnumSetWritable<CreateFlag> flag,
                                 boolean createParent, short replication, long blockSize,
                                 CryptoProtocolVersion[] supportedVersions, String ecPolicyName,
                                 String storagePolicy)
        throws IOException {
        // ...
        try {
            PermissionStatus perm = new PermissionStatus(getRemoteUser()
                                                         .getShortUserName(), null, masked);
            status = namesystem.startFile(src, perm, clientName, clientMachine,
                                          flag.get(), createParent, replication, blockSize, supportedVersions,
                                          ecPolicyName, storagePolicy, cacheEntry != null);
        } finally {
            RetryCache.setState(cacheEntry, status != null, status);
        }
    }
}

@InterfaceAudience.Private
@Metrics(context="dfs")
public class FSNamesystem implements 
    Namesystem, FSNamesystemMBean, NameNodeMXBean, ReplicatedBlocksMBean, ECBlockGroupsMBean {
    HdfsFileStatus startFile(String src, PermissionStatus permissions,
                             String holder, String clientMachine, EnumSet<CreateFlag> flag,
                             boolean createParent, short replication, long blockSize,
                             CryptoProtocolVersion[] supportedVersions, String ecPolicyName,
                             String storagePolicy, boolean logRetryCache) throws IOException {
        HdfsFileStatus status;
        try {
            status = startFileInt(src, permissions, holder, clientMachine, flag,
                                  createParent, replication, blockSize, supportedVersions, ecPolicyName,
                                  storagePolicy, logRetryCache);
        } catch (AccessControlException e) {
            logAuditEvent(false, "create", src);
            throw e;
        }
        logAuditEvent(true, "create", src, status);
        return status;
    }
    
    private HdfsFileStatus startFileInt(String src,
                                        PermissionStatus permissions, String holder, String clientMachine,
                                        EnumSet<CreateFlag> flag, boolean createParent, short replication,
                                        long blockSize, CryptoProtocolVersion[] supportedVersions,
                                        String ecPolicyName, String storagePolicy, boolean logRetryCache)
        throws IOException {
		// ...
        try {
            // ...
            try {
                stat = FSDirWriteFileOp.startFile(this, iip, permissions, holder,
                                                  clientMachine, flag, createParent, replication, blockSize, feInfo,
                                                  toRemoveBlocks, shouldReplicate, ecPolicyName, storagePolicy,
                                                  logRetryCache);
            } catch (IOException e) {
                skipSync = e instanceof StandbyException;
                throw e;
            } finally {
                dir.writeUnlock();
            }
        } finally {
            // ...
        }
    }
}

class FSDirWriteFileOp {
    static HdfsFileStatus startFile(
        FSNamesystem fsn, INodesInPath iip,
        PermissionStatus permissions, String holder, String clientMachine,
        EnumSet<CreateFlag> flag, boolean createParent,
        short replication, long blockSize,
        FileEncryptionInfo feInfo, INode.BlocksMapUpdateInfo toRemoveBlocks,
        boolean shouldReplicate, String ecPolicyName, String storagePolicy,
        boolean logRetryEntry)
        throws IOException {
        // ...
        if (parent != null) {
            iip = addFile(fsd, parent, iip.getLastLocalName(), permissions,
                          replication, blockSize, holder, clientMachine, shouldReplicate,
                          ecPolicyName, storagePolicy);
            newNode = iip != null ? iip.getLastINode().asFile() : null;
        }
        // ...
    }
    
    private static INodesInPath addFile(
        FSDirectory fsd, INodesInPath existing, byte[] localName,
        PermissionStatus permissions, short replication, long preferredBlockSize,
        String clientName, String clientMachine, boolean shouldReplicate,
        String ecPolicyName, String storagePolicy) throws IOException {
        // ...
		try {
            INodeFile newNode = newINodeFile(fsd.allocateNewInodeId(), permissions,
                                             modTime, modTime, replicationFactor, ecPolicyID, preferredBlockSize,
                                             storagepolicyid, blockType);
            newNode.setLocalName(localName);
            newNode.toUnderConstruction(clientName, clientMachine);
            newiip = fsd.addINode(existing, newNode, permissions.getPermission());
        } finally {
            // ...
        }
        // ...
    }
}

@InterfaceAudience.Private
public class FSDirectory implements Closeable {    
    INodesInPath addINode(INodesInPath existing, INode child, FsPermission modes) 
        throws QuotaExceededException, UnresolvedLinkException {
        cacheName(child);
        writeLock();
        try {
            return addLastINode(existing, child, modes, true);
        } finally {
            writeUnlock();
        }
    }
}
```

#### 3.1.2 DateStreamer 启动流程

```java
@InterfaceAudience.Private
public class DFSClient implements 
    java.io.Closeable, RemotePeerFactory, DataEncryptionKeyFactory, KeyProviderTokenIssuer {
    public DFSOutputStream create(String src, FsPermission permission,
                                  EnumSet<CreateFlag> flag, boolean createParent, short replication,
                                  long blockSize, Progressable progress, int buffersize,
                                  ChecksumOpt checksumOpt, InetSocketAddress[] favoredNodes,
                                  String ecPolicyName, String storagePolicy)
        throws IOException {
		// ...
        final DFSOutputStream result = 
            DFSOutputStream.newStreamForCreate(this, 
                                               src, 
                                               masked, 
                                               flag, 
                                               createParent, 
                                               replication, 
                                               blockSize, 
                                               progress,
                                               dfsClientConf.createChecksum(checksumOpt),
                                               getFavoredNodesStr(favoredNodes), 
                                               ecPolicyName, 
                                               storagePolicy);
    }
}

@InterfaceAudience.Private
public class DFSOutputStream extends FSOutputSummer
    implements Syncable, CanSetDropBehind, StreamCapabilities {
    static DFSOutputStream newStreamForCreate(DFSClient dfsClient, String src,
                                              FsPermission masked, EnumSet<CreateFlag> flag, boolean createParent,
                                              short replication, long blockSize, Progressable progress,
                                              DataChecksum checksum, String[] favoredNodes, String ecPolicyName,
                                              String storagePolicy)
        throws IOException {
        // ...
        if(stat.getErasureCodingPolicy() != null) {
            out = new DFSStripedOutputStream(dfsClient, src, stat,
                                             flag, progress, checksum, favoredNodes);
        } else {
            out = new DFSOutputStream(dfsClient, src, stat,
                                      flag, progress, checksum, favoredNodes, true);
        }
        out.start();
        return out;
    }
    
    protected DFSOutputStream(DFSClient dfsClient, String src,
                              HdfsFileStatus stat, EnumSet<CreateFlag> flag, Progressable progress,
                              DataChecksum checksum, String[] favoredNodes, boolean createStreamer) {
        this(dfsClient, src, flag, progress, stat, checksum);
        this.shouldSyncBlock = flag.contains(CreateFlag.SYNC_BLOCK);
        computePacketChunkSize(dfsClient.getConf().getWritePacketSize(),
                               bytesPerChecksum);
        if (createStreamer) {
            streamer = new DataStreamer(stat, null, dfsClient, src, progress,
                                        checksum, cachingStrategy, byteArrayManager, favoredNodes,
                                        addBlockFlags);
        }
    }
    
    protected void computePacketChunkSize(int psize, int csize) {
        final int bodySize = psize - PacketHeader.PKT_MAX_HEADER_LEN;
        final int chunkSize = csize + getChecksumSize();
        chunksPerPacket = Math.max(bodySize/chunkSize, 1);
        packetSize = chunkSize*chunksPerPacket;
        DFSClient.LOG.debug("computePacketChunkSize: src={}, chunkSize={}, "
                            + "chunksPerPacket={}, packetSize={}",
                            src, chunkSize, chunksPerPacket, packetSize);
    }
    
    protected synchronized void start() {
        getStreamer().start();
    }
}

@InterfaceAudience.Private
class DataStreamer extends Daemon {
    @Override
    public void run() {
		while (!streamerClosed && dfsClient.clientRunning) {
            try {
                synchronized (dataQueue) {
                    while ((!shouldStop() && dataQueue.isEmpty()) || doSleep) {
                        long timeout = 1000;
                        if (stage == BlockConstructionStage.DATA_STREAMING) {
                            timeout = sendHeartbeat();
                        }
                        try {
                            dataQueue.wait(timeout);
                        } catch (InterruptedException  e) {
                            LOG.debug("Thread interrupted", e);
                        }
                        doSleep = false;
                    }
                    // ...
                }
            } catch (Throwable e) {
                // ...
            } finally {
                // ...
            }
        }
        closeInternal();
    }
}
```

### 3.2 Write

#### 3.2.1 向 DataStreamer 的队列里面写数据

#### 3.2.2 建立管道 - 机架感知

#### 3.2.3 建立管道 - Socket 发送

#### 3.2.4 建立管道 - Socket 接收

#### 3.2.5 客户端接收 DN 写数据应答 Response

## 4. Yarn 源码解析

## 5. MapReduce 源码解析

## 6. Hadoop 源码解析

