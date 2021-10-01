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

## 3. HDFS 启动源码解析

### 4. Yarn 源码解析

## 5. MapReduce 源码解析

## 6. Hadoop 源码解析

