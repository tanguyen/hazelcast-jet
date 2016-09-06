/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.impl.statemachine.runner.processors;

import com.hazelcast.jet.impl.runtime.VertexRunnerPayloadProcessor;
import com.hazelcast.jet.impl.runtime.VertexRunner;
import com.hazelcast.jet.impl.runtime.runner.VertexRunnerEvent;

public final class VertexRunnerPayloadFactory {
    private VertexRunnerPayloadFactory() {
    }

    public static VertexRunnerPayloadProcessor getProcessor(VertexRunnerEvent event,
                                                            VertexRunner runner) {
        switch (event) {
            case START:
                return new VertexRunnerStartProcessor(runner);
            case EXECUTE:
                return new VertexRunnerExecuteProcessor();
            case INTERRUPT:
                return new VertexRunnerInterruptProcessor(runner);
            case INTERRUPTED:
                return new VertexRunnerInterruptedProcessor(runner);
            case EXECUTION_COMPLETED:
                return new VertexRunnerExecutionCompletedProcessor(runner);
            default:
                return null;
        }
    }
}
