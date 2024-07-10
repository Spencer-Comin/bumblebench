declare -a benchs=(
    GetBooleanAcquireBench
    GetBooleanBench
    GetBooleanOpaqueBench
    GetBooleanVolatileBench
    GetByteAcquireBench
    GetByteBench
    GetByteOpaqueBench
    GetByteVolatileBench
    GetCharAcquireBench
    GetCharBench
    GetCharOpaqueBench
    GetCharVolatileBench
    GetDoubleAcquireBench
    GetDoubleBench
    GetDoubleOpaqueBench
    GetDoubleVolatileBench
    GetFloatAcquireBench
    GetFloatBench
    GetFloatOpaqueBench
    GetFloatVolatileBench
    GetIntAcquireBench
    GetIntBench
    GetIntOpaqueBench
    GetIntVolatileBench
    GetLongAcquireBench
    GetLongBench
    GetLongOpaqueBench
    GetLongVolatileBench
    GetReferenceAcquireBench
    GetReferenceBench
    GetReferenceOpaqueBench
    GetReferenceVolatileBench
    GetShortAcquireBench
    GetShortBench
    GetShortOpaqueBench
    GetShortVolatileBench
    PutBooleanBench
    PutBooleanOpaqueBench
    PutBooleanReleaseBench
    PutBooleanVolatileBench
    PutByteBench
    PutByteOpaqueBench
    PutByteReleaseBench
    PutByteVolatileBench
    PutCharBench
    PutCharOpaqueBench
    PutCharReleaseBench
    PutCharVolatileBench
    PutDoubleBench
    PutDoubleOpaqueBench
    PutDoubleReleaseBench
    PutDoubleVolatileBench
    PutFloatBench
    PutFloatOpaqueBench
    PutFloatReleaseBench
    PutFloatVolatileBench
    PutIntBench
    PutIntOpaqueBench
    PutIntReleaseBench
    PutIntVolatileBench
    PutLongBench
    PutLongOpaqueBench
    PutLongReleaseBench
    PutLongVolatileBench
    PutReferenceBench
    PutReferenceOpaqueBench
    PutReferenceReleaseBench
    PutReferenceVolatileBench
    PutShortBench
    PutShortOpaqueBench
    PutShortReleaseBench
    PutShortVolatileBench
)

mkdir semeru-results
mkdir temurin-results

for bench in "${benchs[@]}"
do
    $SEMERU/bin/java --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -jar BumbleBench.jar $bench | tee semeru-results/$bench-results.txt
    $TEMURIN/bin/java --add-exports java.base/jdk.internal.misc=ALL-UNNAMED --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -jar BumbleBench.jar $bench | tee temurin-results/$bench-results.txt
done
