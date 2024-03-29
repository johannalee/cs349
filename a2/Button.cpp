// Author: Michael Terry
#include "Button.h"
#include "XWindow.h"
#include "Logging.h"

#include <algorithm>

using namespace cs349;

Button::Button(const string & name, const string & label)
  : Component(name)
{
  // Perform any initialization needed here
  this->label = label;
  buttontype = DEFAULT;


// TODO CS349
}

void Button::AddActionListener(ActionListener* l) {
// TODO CS349
  listeners.push_back(l);
}

void Button::RemoveActionListener(ActionListener* l) {
// TODO CS349
  remove(listeners.begin(), listeners.end(), l);
}

string Button::GetLabel() const {
  return label;
}

void Button::SetLabel(const string & label) {
  this->label = label;
}

//Returns the type of button image
Button::ButtonType Button::GetButtonType() const{
	return buttontype;
}

//Sets the type of button image
void Button::SetButtonType(ButtonType buttontype){
	this->buttontype = buttontype;
}

void Button::PaintComponent(Graphics* g) {
// TODO CS349
  g->SetForegroundColor(0x000000);
  g->FillRect(Rectangle(0, 0, 50, 50));

  g->SetForegroundColor(0xFFFFFF);

  vector<Point> vertices;

  switch(this->GetButtonType()){
  	//TO_END is just adding one side bar on the right side of the FOWARD button image
  	case TO_END:
  		g->FillRect(Rectangle(55, 0, 5, 50));
  	case FORWARD:
	    vertices.push_back(Point(0,0));
	    vertices.push_back(Point(50,25));
	    vertices.push_back(Point(0,50));
  	break;

  	case PLAY:
	    vertices.push_back(Point(0,0));
	    vertices.push_back(Point(70,35));
	    vertices.push_back(Point(0,70));
  	break;

  	case TO_START:
  		g->FillRect(Rectangle(0, 0, 5, 50));
			vertices.push_back(Point(60,0));
			vertices.push_back(Point(10,25));
			vertices.push_back(Point(60,50));
    break;

  	case BACKWARD:
			vertices.push_back(Point(50,0));
			vertices.push_back(Point(50,50));
			vertices.push_back(Point(0,25));
  	break;

  	default:
		 LOG_WARN << "Warning: Set your button type";
  	break;
  }
	g->FillPolygon(vertices);

}

// TODO CS349: Implement any other methods needed here
// TODO CS349
