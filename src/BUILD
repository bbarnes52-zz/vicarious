load("//appengine:java_appengine.bzl", "appengine_war")

appengine_war(
    name = "vicarious",
    data = ["//src/server/webapp"],
    data_path = "/src/server/webapp",
    jars = ["//src/server/java:servlet_deploy.jar"],
)
