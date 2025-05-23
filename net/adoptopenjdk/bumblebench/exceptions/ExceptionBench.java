/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.exceptions;

import net.adoptopenjdk.bumblebench.core.MicroBench;

public class ExceptionBench {
    private static volatile long counter;

    public static class BuildAndThrow extends MicroBench {
        protected long doBatch(long numIterations) {
            counter = 0;

            for (long i = 0; i < numIterations; i++) {
                try {
                    throw new Exception("This is an exception");
                } catch (Exception e) {
                    counter++;
                }
            }

            return counter;
        }
    }

    public static class Build extends MicroBench {
        public volatile Exception e;
        protected long doBatch(long numIterations) {
            for (long i = 0; i < numIterations; i++) {
                e = new Exception("This is an exception");
            }

            return numIterations;
        }
    }

    public static class Throw extends MicroBench {
        private static final Exception e = new Exception("This is an exception");
        protected long doBatch(long numIterations) {
            counter = 0;

            for (long i = 0; i < numIterations; i++) {
                try {
                    throw e;
                } catch (Exception e) {
                    counter++;
                }
            }

            return counter;
        }
    }

    public static class AIOOBE extends MicroBench {
        public static volatile long total;
        public static int[] arr = new int[0];
        public static volatile int dump = 0;

        protected long doBatch(long numIterations) {
            counter = 0;

            for (long i = 0; i < numIterations; i++) {
                try {
                    total += arr[(int) i];
                } catch (ArrayIndexOutOfBoundsException e) {
                    counter++;
                    dump += e.hashCode();
                }
            }

            return counter;
        }
    }
}
