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
package com.ok2c.lightmtp.impl.protocol.cmd;

import java.util.concurrent.Future;

import org.apache.http.concurrent.BasicFuture;
import org.apache.http.concurrent.FutureCallback;

import com.ok2c.lightmtp.SMTPReply;
import com.ok2c.lightmtp.impl.protocol.ServerState;
import com.ok2c.lightmtp.protocol.Action;

public class SimpleAction implements Action<ServerState> {

    private final SMTPReply reply;

    public SimpleAction(final SMTPReply reply) {
        super();
        this.reply = reply;
    }

    @Override
    public Future<SMTPReply> execute(
            final ServerState state,
            final FutureCallback<SMTPReply> callback) {
        BasicFuture<SMTPReply> future = new BasicFuture<SMTPReply>(callback);
        future.completed(this.reply);
        return future;
    }

}
