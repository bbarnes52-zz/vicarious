goog.provide('vicarious.randomnumberservice');

goog.require('goog.net.XhrIo');
goog.require('goog.Promise');


/**
 * Makes GET request to random number service.
 * @return {!goog.Promise<number>}
 * @export
 */
vicarious.randomnumberservice.getRandomNumber =
    function() {
  return new goog.Promise(function(resolve, reject) {
    const url = '/random';
    goog.net.XhrIo.send(url, goog.bind(this._callback, this, resolve, reject));
  }, this);
}


/**
* Makes POST request to random number service.
* @param {number} minVal
* @param {number} maxVal
* @return {!goog.Promise<number>}
* @export
*/
vicarious.randomnumberservice.getRandomNumberInRange =
    function(minVal, maxVal) {
  return new goog.Promise(function(resolve, reject) {
    const url = '/random';
    let body = new Object();
    body.minVal = minVal;
    body.maxVal = maxVal;
    const headers = {'Content-Type': 'application/json'};
    goog.net.XhrIo.send(
        url, goog.bind(this._callback, this, resolve, reject), 'POST',
        JSON.stringify(body), headers);
  }, this);
}


/**
* Callback for requests to random number service.
* @param {!Object|!goog.structs.Map=} e
* @private
*/
vicarious.randomnumberservice._callback = function(resolve, reject, e) {
  const result = e.target;
  if (result.isSuccess()) {
    resolve(result.getResponseJson().retval);
  } else {
    // TODO(bgb): Consider implementing exponential backoff.
    reject(result.getLastError());
  }
}
