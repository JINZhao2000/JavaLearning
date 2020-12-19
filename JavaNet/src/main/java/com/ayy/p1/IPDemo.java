package com.ayy.p1;

import java.net.*;
import java.util.Enumeration;

/**
 * @ ClassName IPDemo
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 21H
 * @ Version 1.0
 */
public class IPDemo {
    public static void main(String[] args) throws UnknownHostException {
        // Hostname: LAPTOP-KUJHF1L7 ,Host Address: 192.168.137.1 -> VirtualBox
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println("Hostname: "+addr.getHostName()+
                " ,Host Address: "+addr.getHostAddress());
        addr = IPDemo.getLocalHostLANAddress();
        // Hostname: LAPTOP-KUJHF1L7.mshome.net ,Host Address: 192.168.137.1 -> VirtualBox
        System.out.println("Hostname: "+addr.getHostName()+
                " ,Host Address: "+addr.getHostAddress());
        // 192.168.1.41 real ip
        System.out.println(IPDemo.getRealIP());
        // get info by name dn/ip
        addr = InetAddress.getByName("www.google.fr");
        System.out.println("Hostname: "+addr.getHostName()+
                " ,Host Address: "+addr.getHostAddress());
    }

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // iterator of all port
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // iterator of all ip addr of a port
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // remove 127.0.0.1 loopback address from result
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    public static String getRealIP() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
                        .nextElement();
                // remove loopback, sub-ports, no-running ports
                if (netInterface.isLoopback() || netInterface.isVirtual()
                        || !netInterface.isUp()) {
                    continue;
                }
                if (!netInterface.getDisplayName().contains("Intel")
                        && !netInterface.getDisplayName().contains("Realtek")) {
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface
                        .getInetAddresses();
                System.out.println(netInterface.getDisplayName());
                while (addresses.hasMoreElements()) {
                    InetAddress ip = addresses.nextElement();
                    if (ip != null) {
                        // ipv4
                        if (ip instanceof Inet4Address) {
                            System.out.println("ipv4 = " + ip.getHostAddress());
                            return ip.getHostAddress();
                        }
                    }
                }
                break;
            }
        } catch (SocketException e) {
            System.err.println("Error when getting host ip address"
                    + e.getMessage());
        }
        return null;
    }
}
