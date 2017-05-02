db.tweets.mapReduce(
  function() {
    var regex = /@\w+/g;
    var str = this.text;
    var m;
    while ((m = regex.exec(str)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }
      // The result can be accessed through the `m`-variable.
      m.forEach(function(match, groupIndex) {
        emit(match, 1);
      });
    }
  },
  function(key, values) {
    var sum = 0;
    values.forEach(function(value) {
      sum += value;
    });
    return sum;
  },
  {
    out: { merge: "most_mentioned" }
  }
);
