/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ok2c.lightmtp.impl.agent;

import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.nio.reactor.IOSession;
import org.apache.http.util.Args;

import com.ok2c.lightmtp.impl.protocol.ServerSession;
import com.ok2c.lightmtp.protocol.SessionFactory;

public class ServerIOEventDispatch implements IOEventDispatch {

    private static final String SERVER_SESSION = "smtp.server-session";

    private final IOSessionRegistry sessionRegistry;
    private final SessionFactory<ServerSession> sessionFactory;

    public ServerIOEventDispatch(
            final IOSessionRegistry sessionRegistry,
            final SessionFactory<ServerSession> sessionFactory) {
        super();
        Args.notNull(sessionRegistry, "I/O session registry");
        Args.notNull(sessionFactory, "Session factory");
        this.sessionRegistry = sessionRegistry;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void connected(final IOSession iosession) {
        ServerSession serverSession = this.sessionFactory.create(iosession);
        iosession.setAttribute(SERVER_SESSION, serverSession);
        serverSession.connected();
        this.sessionRegistry.add(iosession);
    }

    @Override
    public void disconnected(final IOSession iosession) {
        ServerSession serverSession = (ServerSession) iosession.getAttribute(SERVER_SESSION);
        if (serverSession != null) {
            serverSession.disconneced();
        }
        this.sessionRegistry.remove(iosession);
    }

    @Override
    public void inputReady(final IOSession iosession) {
        ServerSession serverSession = (ServerSession) iosession.getAttribute(SERVER_SESSION);
        serverSession.consumeData();
    }

    @Override
    public void outputReady(final IOSession iosession) {
        ServerSession serverSession = (ServerSession) iosession.getAttribute(SERVER_SESSION);
        serverSession.produceData();
    }

    @Override
    public void timeout(final IOSession iosession) {
        ServerSession serverSession = (ServerSession) iosession.getAttribute(SERVER_SESSION);
        serverSession.timeout();
    }

}
