load("@io_bazel_rules_closure//closure:defs.bzl", "closure_js_library")

closure_js_library(
    name = "random-number-service",
    srcs = ["random-number-service.js"],
    suppress = [
        "lintChecks",
        "reportUnknownTypes",
        "missingSourcesWarnings",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "@io_bazel_rules_closure//closure/library",
    ],
)
