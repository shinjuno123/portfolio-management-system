// The amount of symbol we want to place;
var count = 50;

// Create a symbol, which we will use to place instances of later:
var path = new Path.Circle({
  center: [0, 0],
  radius: 5,
  fillColor: "white",
  strokeColor: "black",
});

var symbol = new Symbol(path);

// Place the instances of the symbol:
for (var i = 0; i < count; i++) {
  // The center position is a random point in the view:
  var center = Point.random() * view.size;
  var placed = symbol.place(center);
  placed.scale(i / count + 0.001);
  placed.data.vector = new Point({
    angle: Math.random() * 360,
    length: ((i / count) * Math.random()) / 5,
  });
}

var vector = new Point({
  angle: 45,
  length: 0,
});

var mouseVector = vector.clone();

// The onFrame function is called up to 60 times a second:
function onFrame(event) {
  vector = vector + (mouseVector - vector) / 30;

  // Run through the active layer's children list and change
  // the position of the placed symbols:
  for (var i = 0; i < count; i++) {
    var item = project.activeLayer.children[i];
    var size = item.bounds.size;
    var length = ((vector.length / 10) * size.width) / 10;
    item.position += vector.normalize(length) + item.data.vector;
    keepInView(item);
  }

  mouseVector = (view.center - Math.random() * 10) / 30;
}

function keepInView(item) {
  var position = item.position;
  var viewBounds = view.bounds;
  if (position.isInside(viewBounds)) return;
  var itemBounds = item.bounds;
  if (position.x > viewBounds.width + 5) {
    position.x = -item.bounds.width;
  }

  if (position.x < -itemBounds.width - 5) {
    position.x = viewBounds.width;
  }

  if (position.y > viewBounds.height + 5) {
    position.y = -itemBounds.height;
  }

  if (position.y < -itemBounds.height - 5) {
    position.y = viewBounds.height;
  }
}
