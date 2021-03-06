/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.jet;

import com.hazelcast.jet.impl.util.ArrayDequeInbox;
import com.hazelcast.jet.impl.util.ArrayDequeOutbox;
import com.hazelcast.jet.impl.util.ProgressTracker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StreamingTestSupport {
    public ArrayDequeInbox inbox = new ArrayDequeInbox();
    public ArrayDequeOutbox outbox = new ArrayDequeOutbox(new int[] {1024}, new ProgressTracker());

    protected static Watermark wm(long timestamp) {
        return new Watermark(timestamp);
    }

    protected void assertOutbox(List<?> items) {
        String expected = streamToString(items.stream());
        String actual = streamToString(outbox.queueWithOrdinal(0).stream());
        outbox.queueWithOrdinal(0).clear();

        assertEquals(expected, actual);
    }

    static String streamToString(Stream<?> stream) {
        return stream
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }

    protected Object pollOutbox() {
        return outbox.queueWithOrdinal(0).poll();
    }
}
