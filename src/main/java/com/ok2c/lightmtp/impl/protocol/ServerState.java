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
package com.ok2c.lightmtp.impl.protocol;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.ok2c.lightmtp.SMTPExtensions;

public class ServerState {

    private final Set<String> extensions;
    private final String serverId;
    private final LinkedList<String> recipients;

    private ClientType clientType;
    private InetAddress client;
    private String clientDomain;
    private String messageId;
    private String sender;
    private DataType dataType;
    private MIMEEncoding mimeEncoding;
    private boolean terminated;

    public ServerState(final String serverId) {
        super();
        Set<String> exts = new HashSet<String>();
        exts.add(SMTPExtensions.ENHANCEDSTATUSCODES);
        exts.add(SMTPExtensions.MIME_8BIT);
        exts.add(SMTPExtensions.PIPELINING);
        this.extensions = Collections.unmodifiableSet(exts);
        this.serverId = serverId;
        this.recipients = new LinkedList<String>();
    }

    public String getServerId() {
        return this.serverId;
    }

    public Set<String> getExtensions() {
        return this.extensions;
    }

    public InetAddress getClient() {
        return this.client;
    }

    public void setClientAddress(final InetAddress address) {
        this.client = address;
    }

    public ClientType getClientType() {
        return this.clientType;
    }

    public void setClientType(final ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientDomain() {
        return this.clientDomain;
    }

    public void setClientDomain(final String clientDomain) {
        this.clientDomain = clientDomain;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(final String sender) {
        this.sender = sender;
    }

    public List<String> getRecipients() {
        return this.recipients;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(final DataType dataType) {
        this.dataType = dataType;
    }

    public MIMEEncoding getMimeEncoding() {
        return this.mimeEncoding;
    }

    public void setMimeEncoding(final MIMEEncoding mimeEncoding) {
        this.mimeEncoding = mimeEncoding;
    }

    public boolean isTerminated() {
        return this.terminated;
    }

    public void terminated() {
        this.terminated = true;
    }

    public void reset() {
        this.sender = null;
        this.messageId = null;
        this.recipients.clear();
        this.dataType = null;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[extensions: ");
        buffer.append(this.extensions);
        buffer.append("][client type: ");
        buffer.append(this.clientType);
        buffer.append("][client id: ");
        buffer.append(this.clientDomain);
        buffer.append("][message id: ");
        buffer.append(this.messageId);
        buffer.append("][sender: ");
        buffer.append(this.sender);
        buffer.append("][recipients: ");
        buffer.append(this.recipients);
        buffer.append("][data type: ");
        buffer.append(this.dataType);
        buffer.append("]");
        return buffer.toString();
    }

}
