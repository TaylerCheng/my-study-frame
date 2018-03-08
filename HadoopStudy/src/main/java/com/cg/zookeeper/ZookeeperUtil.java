package com.cg.zookeeper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class ZookeeperUtil {

	private static final int SESSION_TIMEOUT = 5000;
	private static final Charset CHARSET = Charset.forName("UTF-8");

	private static ConnectionWatcher cw = new ConnectionWatcher();
	private static ZooKeeper zk;

	private ZookeeperUtil() {
	}

	private static void connect(String host) {
		try {
			cw.connect(host);
			zk = cw.getZooKeeper();
			ZooKeeper.States state = zk.getState();
			System.out.println(state);
			Thread.sleep(1000);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void createGroup(String groupName) throws KeeperException, InterruptedException {
		zk.create(groupName, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	private static void createNode(String groupName, String memberName,String data)
			throws KeeperException, InterruptedException {
		if (!groupName.endsWith("/")) {
			groupName += "/";
		}
		String path = groupName + memberName;
		zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	private static void list(String groupName) {
		try {
			List<String> children = zk.getChildren(groupName, false);
			if (!children.isEmpty()) {
				for (String child : children) {
					if (!groupName.endsWith("/")) {
						groupName += "/";
					}
					String path = groupName + child;
					byte[] data = zk.getData(path, cw, null);
					if (data == null) {
						System.out.print("(D)" + path);
					} else {
						System.out.print( path + " -> " + new String(data));
					}
					System.out.println();
					list(path);
				}
			}

		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void delete(String path) throws InterruptedException,
			KeeperException {
		zk.delete(path, -1);
	}

	private static void setData(String path, String value)
			throws KeeperException, InterruptedException {
		byte[] data = value.getBytes();
		zk.setData(path, data, -1);
	}

	private static String getData(String path) throws KeeperException,
			InterruptedException {
		byte[] data = zk.getData(path, cw, null);
		System.out.println(new String(data, CHARSET));
		return data.toString();
	}

	private static void close() throws InterruptedException {
		zk.close();
	}

	private static void createNode(String path) throws KeeperException,
			InterruptedException {
		zk.create(path, "12345".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
	}

	private static void getACL(String path) throws KeeperException,
			InterruptedException {
		System.out.println(zk.getACL(path, new Stat()));
	}

	public static void main(String[] args) throws Exception {
		ZookeeperUtil.connect("slave1.hadoop:2181");
//		ZookeeperUtil.createGroup("/cheng");
//		ZookeeperUtil.createNode("/cheng", "pers", "123456");
//		ZookeeperUtil.getData("/cheng/pers");
//		ZookeeperUtil.createNode("/cheng/pers", "temp", "123");
//		ZookeeperUtil.list("/cheng");
//		ZookeeperUtil.delete("/cheng/pers");
//		ZookeeperUtil.list("/cheng");

		// ZookeeperUtil.createNode("/cheng","xcg_");
		// ZookeeperUtil.setData("/cheng", "abcde");
		// ZookeeperUtil.delete("/hive_zookeeper_namespace/");
		// ZookeeperUtil.getData("/cheng");
		//ZookeeperUtil.list("/hiveserver2");
		// ZookeeperUtil.createNode("/cheng");
		// ZookeeperUtil.delete("/cheng");
		// ZookeeperUtil.getACL("/cheng");
		// ZookeeperUtil.close();
		//ZookeeperUtil.setData("/cheng", "abcde");
//		Thread.currentThread().sleep(Long.MAX_VALUE);
	}

}
