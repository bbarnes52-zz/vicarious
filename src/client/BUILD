load("@io_bazel_rules_closure//closure:defs.bzl", "closure_js_library", "closure_js_binary")

closure_js_binary(
    name = "js-bin",
    compilation_level = "SIMPLE",
    entry_points = ["vicarious.app"],
    deps = [":app"],
)

closure_js_binary(
    name = "js-bin-dev",
    compilation_level = "WHITESPACE_ONLY",
    entry_points = ["vicarious.app"],
    deps = [":app"],
)

closure_js_library(
    name = "app",
    srcs = ["app.js"],
    suppress = [
        "lintChecks",
        "missingSourcesWarnings",
        "globalThis",
    ],
    deps = [
        "//services:random-number-service",
        "//templates:soy",
        "@io_bazel_rules_closure//closure/library",
    ],
)
