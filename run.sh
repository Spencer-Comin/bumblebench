SAVEIFS="$IFS"
IFS=$'\n'
benchs=$(java -jar BumbleBench.jar -list)
IFS="$SAVEIFS"

mkdir semeru-results
mkdir temurin-results

for bench in $benchs
do
    $SEMERU/bin/java --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -jar BumbleBench.jar $bench | tee semeru-results/$bench-results.txt
    $TEMURIN/bin/java --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -jar BumbleBench.jar $bench | tee temurin-results/$bench-results.txt
done
