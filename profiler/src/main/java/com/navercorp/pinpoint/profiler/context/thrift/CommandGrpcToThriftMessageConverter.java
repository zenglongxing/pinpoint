/*
 * Copyright 2019 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.context.thrift;

import com.navercorp.pinpoint.grpc.trace.PCmdActiveThreadCountRes;
import com.navercorp.pinpoint.grpc.trace.PCmdEchoResponse;
import com.navercorp.pinpoint.thrift.dto.command.TCmdActiveThreadCountRes;
import com.navercorp.pinpoint.thrift.dto.command.TCommandEcho;
import org.apache.thrift.TBase;

import java.util.List;

/**
 * @author Taejin Koo
 */
public class CommandGrpcToThriftMessageConverter implements MessageConverter<TBase> {

    @Override
    public TBase toMessage(Object message) {
        if (message instanceof PCmdEchoResponse) {
            return buildTCommandEcho((PCmdEchoResponse) message);
        } else if (message instanceof PCmdActiveThreadCountRes) {
            return buildTCmdActiveThreadCountRes((PCmdActiveThreadCountRes) message);
        }
        return null;
    }

    private TCommandEcho buildTCommandEcho(PCmdEchoResponse echoMessage) {
        String message = echoMessage.getMessage();
        return new TCommandEcho(message);
    }

    private TCmdActiveThreadCountRes buildTCmdActiveThreadCountRes(PCmdActiveThreadCountRes activeThreadCountRes) {
        int histogramSchemaType = activeThreadCountRes.getHistogramSchemaType();
        List<Integer> activeThreadCountList = activeThreadCountRes.getActiveThreadCountList();
        long timeStamp = activeThreadCountRes.getTimeStamp();

        TCmdActiveThreadCountRes result = new TCmdActiveThreadCountRes(histogramSchemaType, activeThreadCountList);
        result.setTimeStamp(timeStamp);

        return result;
    }

}
