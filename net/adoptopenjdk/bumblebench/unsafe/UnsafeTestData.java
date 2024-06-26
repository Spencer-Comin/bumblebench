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

package net.adoptopenjdk.bumblebench.unsafe;

import jdk.internal.misc.Unsafe;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

public class UnsafeTestData {
    public static class TestObject {
        static Object  STATIC_OBJECT;
        static int     STATIC_INT;
        static boolean STATIC_BOOLEAN;
        static byte    STATIC_BYTE;
        static short   STATIC_SHORT;
        static char    STATIC_CHAR;
        static long    STATIC_LONG;
        static float   STATIC_FLOAT;
        static double  STATIC_DOUBLE;

        Object  OBJECT;
        int     INT;
        boolean BOOLEAN;
        byte    BYTE;
        short   SHORT;
        char    CHAR;
        long    LONG;
        float   FLOAT;
        double  DOUBLE;
    }

    public static class Offsets {
        public final Object staticFieldBase;
        public final long staticFieldOffset;
        public final long fieldOffset;
        public final int arrayOffset;
        public final int arrayShift;

        public Offsets(Unsafe UNSAFE, String fieldName, Class<?> arrayClass) throws NoSuchFieldException {
            Field staticField = TestObject.class.getDeclaredField("STATIC_"+fieldName);
            staticFieldBase = UNSAFE.staticFieldBase(staticField);
            staticFieldOffset = UNSAFE.staticFieldOffset(staticField);

            Field field = TestObject.class.getDeclaredField(fieldName);
            fieldOffset = UNSAFE.objectFieldOffset(field);

            arrayOffset = UNSAFE.arrayBaseOffset(arrayClass);
            int ascale = UNSAFE.arrayIndexScale(arrayClass);
            arrayShift = 31 - Integer.numberOfLeadingZeros(ascale);
        }
    }

    public static final Unsafe UNSAFE;

    public static final Offsets referenceOffsets;
    public static final Offsets intOffsets;
    public static final Offsets booleanOffsets;
    public static final Offsets byteOffsets;
    public static final Offsets shortOffsets;
    public static final Offsets charOffsets;
    public static final Offsets longOffsets;
    public static final Offsets floatOffsets;
    public static final Offsets doubleOffsets;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
        } catch (IllegalAccessError e) {
            System.err.println("add \"--add-exports java.base/jdk.internal.misc=ALL-UNNAMED\" to your java command");
            throw new RuntimeException("Unable to get Unsafe instance.", e);
        } catch (InaccessibleObjectException e) {
            System.err.println("add \"--add-opens java.base/jdk.internal.misc=ALL-UNNAMED\" to your java command");
            throw new RuntimeException("Unable to get Unsafe instance.", e);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get Unsafe instance.", e);
        }

        try {
            referenceOffsets = new Offsets(UNSAFE, "OBJECT", Object[].class);
            intOffsets = new Offsets(UNSAFE, "INT", int[].class);
            booleanOffsets = new Offsets(UNSAFE, "BOOLEAN", boolean[].class);
            byteOffsets = new Offsets(UNSAFE, "BYTE", byte[].class);
            shortOffsets = new Offsets(UNSAFE, "SHORT", short[].class);
            charOffsets = new Offsets(UNSAFE, "CHAR", char[].class);
            longOffsets = new Offsets(UNSAFE, "LONG", long[].class);
            floatOffsets = new Offsets(UNSAFE, "FLOAT", float[].class);
            doubleOffsets = new Offsets(UNSAFE, "DOUBLE", double[].class);
        } catch (NoSuchFieldException e) {
            // Should never happen
            throw new RuntimeException(e);
        }
    }
}
