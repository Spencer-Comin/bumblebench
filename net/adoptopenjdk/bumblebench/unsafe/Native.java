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
import java.io.File;
import java.nio.file.*;
import java.io.InputStream;

public class Native {
    static {
        try {
            InputStream is = Native.class.getResourceAsStream("libnative.so");
            Path libPath = FileSystems.getDefault().getPath("/tmp", "libtmp.so");
            Files.copy(is, libPath, StandardCopyOption.REPLACE_EXISTING);
            System.load(libPath.toString());
            Files.delete(libPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static native long getOffset1();
    public static native long getOffset2();
    public static native Object getBase1();
    public static native Object getBase2();
}
