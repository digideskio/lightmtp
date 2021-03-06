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

import org.apache.http.nio.reactor.IOSession;

import com.ok2c.lightmtp.protocol.DeliveryRequest;
import com.ok2c.lightmtp.protocol.DeliveryRequestHandler;
import com.ok2c.lightmtp.protocol.DeliveryResult;
import com.ok2c.lightmtp.protocol.SessionContext;

public class SimpleTestDeliveryRequestHandler implements DeliveryRequestHandler {

    @Override
    public void connected(
            final SessionContext context) {
    }

    @Override
    public void disconnected(
            final SessionContext context) {
        SimpleTestJob job = (SimpleTestJob) context.getAttribute(IOSession.ATTACHMENT_KEY);
        if (job != null) {
            job.cancel();
        }
    }

    @Override
    public void completed(
            final DeliveryRequest request,
            final DeliveryResult result,
            final SessionContext context) {
        SimpleTestJob job = (SimpleTestJob) context.getAttribute(IOSession.ATTACHMENT_KEY);
        job.addResult(result);
    }

    @Override
    public void exception(
            final Exception ex, final SessionContext context) {
        SimpleTestJob job = (SimpleTestJob) context.getAttribute(IOSession.ATTACHMENT_KEY);
        job.failure(ex);
    }

    @Override
    public void failed(
            final DeliveryRequest request,
            final DeliveryResult result,
            final SessionContext context) {
        SimpleTestJob job = (SimpleTestJob) context.getAttribute(IOSession.ATTACHMENT_KEY);
        job.addResult(result);
    }

    @Override
    public DeliveryRequest submitRequest(
            final SessionContext context) {
        SimpleTestJob job = (SimpleTestJob) context.getAttribute(IOSession.ATTACHMENT_KEY);
        return job.removeRequest();
    }

}
