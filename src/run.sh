# TODO(bgb): Replace this with a genrule.
ENVIRONMENT=$1
DEPLOY=$2
rm -f ./server/webapp/static/vicarious.soy.js
rm -f ./server/webapp/static/app.js
cd client
if [ "$ENVIRONMENT" == "prod" ]; then
  bazel build :js-bin
  cp bazel-bin/js-bin.js ../server/webapp/static/app.js
  cp ../config/appengine-web-prod.xml ../server/webapp/WEB-INF/appengine-web.xml
else 
  bazel build :js-bin-dev
  cp bazel-bin/js-bin-dev.js ../server/webapp/static/app.js
  cp ../config/appengine-web-dev.xml ../server/webapp/WEB-INF/appengine-web.xml
fi
cd ../
cp client/templates/index.html ./server/webapp/
if [ "$DEPLOY" == "deploy" ]; then
  bazel run :vicarious.deploy
else
  bazel run :vicarious
fi
