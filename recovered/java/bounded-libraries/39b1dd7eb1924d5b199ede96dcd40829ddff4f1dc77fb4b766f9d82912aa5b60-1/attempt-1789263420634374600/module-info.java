module com.github.benmanes.caffeine {
    /* static phase */ requires com.google.errorprone.annotations;
    /* static phase */ requires org.checkerframework.checker.qual;

    exports com.github.benmanes.caffeine.cache;
    exports com.github.benmanes.caffeine.cache.stats;

}

