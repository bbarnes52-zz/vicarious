goog.provide('vicarious.app');

goog.require('goog.dom');
goog.require('goog.soy');
goog.require('vicarious.randomnumberservice');
goog.require('vicarious.template');

// TODO(bgb): Refactor to listen for events.

/**
 * Gets a random number and updates view.
 * @export
 */
vicarious.app.getRandomNumber = function() {
  return vicarious.randomnumberservice.getRandomNumber().then(
      (randomNumber) => {
        this._renderRandomNumberTemplate(randomNumber);
      });
}

/**
 * Gets a random number in the specified range and updates the view.
 * @param {number} minValue
 * @param {number} maxValue
 * @export
 */
vicarious.app.getRandomNumberInRange = function(minValue, maxValue) {
  return vicarious.randomnumberservice
      .getRandomNumberInRange(minValue, maxValue)
      .then((randomNumber) => {
        this._renderRandomNumberTemplate(randomNumber);
      });
}


/**
 * Gets a random number in the specified range and updates the view.
 * @export
 */
vicarious.app.triggerGetRandomNumberInRange = function() {
  let lowerBound = goog.dom.getElement('lower-bound').value;
  let upperBound = goog.dom.getElement('upper-bound').value;
  if (!(lowerBound && upperBound)) {
    alert('Please specify both a lower and upper bound.');
    return
  }
  // TODO(bgb): Handle corner case where unsigned ints wrap around.
  if (lowerBound >= upperBound) {
    alert('Lower bound must be less than upper bound.')
    return;
  }
  this.getRandomNumberInRange(lowerBound, upperBound);
}


/**
 * Renders the random number template with the provided number.
 * @private
 * @param {number} randomNumber
 */
vicarious.app._renderRandomNumberTemplate = function(randomNumber) {
  goog.soy.renderElement(
      goog.dom.getElement('randomIntElement'),
      vicarious.template.randomNumberTemplate, {randomInt: randomNumber})
}
