[Install gcloud](https://cloud.google.com/sdk/downloads)


Authenticate using the google account associated with GCP project `vicarious-201316`.

```
gcloud auth application-default login
```

**NOTE:** App Engine requires additional credentials to deploy new versions.
You will be prompted to authenticate when you attempt to deploy.

### Run dev server locally

```
./run.sh
```

## Run prod server locally

```
./run.sh prod
```

## Deploy dev application to cloud

```
./run.sh dev deploy
```

## Deploy prod application to cloud

```
./run.sh prod deploy
```
