#include "Slider.h"
#include "XWindow.h"
#include "Logging.h"
#include <algorithm>
#include <sstream>

using namespace cs349;
using namespace std;

Slider::Slider(const string & name, OrientationType orientation)
  : Component(name)
{
  lowerBound = 0;
  upperBound = 0;
  curValue = 0;
  lowerBoundLabel = "";
  upperBoundLabel = "";
  thumbLabel = "";
  this->orientation = orientation;
  lineX = 30;
  lineY = 10;
  textY = lineY+40;
  thumbWidth = 7;
  thumbHeight = 20;
  posX = lineX;
  mouseClicked = false;

// TODO CS349
  sliderLength = 280;
}

void Slider::AddValueListener(ValueListener* l) {
// TODO CS349
  listeners.push_back(l);
}

void Slider::RemoveValueListener(ValueListener* l) {
// TODO CS349
  remove(listeners.begin(), listeners.end(), l);
}

int Slider::GetMinimum() const {
  return lowerBound;
}

int Slider::GetMaximum() const {
  return upperBound;
}

int Slider::GetCurValue() const {
  return curValue;
}

void Slider::SetMinimum(int minValue) {
// TODO CS349
  this->lowerBound = minValue;
}

void Slider::SetMaximum(int maxValue) {
// TODO CS349
  this->upperBound = maxValue;
}

void Slider::SetCurValue(int value) {
// TODO CS349
  if(value < 0){
    this->curValue = this->lowerBound;
  }else if(value > 100){
    this->curValue = this->upperBound;
  }else{
    this->curValue = value;
  }
  this->SetPosX();
}

Slider::OrientationType Slider::GetOrientation() const {
  return orientation;
}

void Slider::SetOrientation(OrientationType orientation) {
  this->orientation = orientation;
  this->Repaint();
}

void Slider::PaintComponent(Graphics* g) {
// TODO CS349
  g->SetForegroundColor(0x000000);
  g->FillRect(Rectangle(0, 0, 400, 150));

  g->SetForegroundColor(0xFFFFFF);

  //Slider Line
  g->DrawLine(Point(lineX, lineY), Point(lineX+sliderLength, lineY));

  //Bounds and Thumb Labels
  g->DrawText(Point((lineX-7), textY), this->GetLowerBoundLabel());
  g->DrawText(Point((lineX+sliderLength-7), textY), this->GetUpperBoundLabel());

  stringstream ss;
  ss << this->curValue;
  this->SetThumbLabel(ss.str());
  //thumb
  g->SetForegroundColor(0xFF0000);
  g->DrawText(Point((lineX+(sliderLength/2)), textY), this->GetThumbLabel());

  if(this->mouseClicked){
    this->SetPosX();
  }
    g->SetForegroundColor(0xFFFFFF);
    g->FillRect(Rectangle(posX, (lineY-(thumbHeight/2)), thumbWidth, thumbHeight));

}

string Slider::GetLowerBoundLabel() const {
  return lowerBoundLabel;
}

string Slider::GetUpperBoundLabel() const {
  return upperBoundLabel;
}

string Slider::GetThumbLabel() const {
  return thumbLabel;
}

void Slider::SetLowerBoundLabel(const string & label) {
  this->lowerBoundLabel = label;
  this->Repaint();
}

void Slider::SetUpperBoundLabel(const string & label) {
  this->upperBoundLabel = label;
  this->Repaint();
}

void Slider::SetThumbLabel(const string & label) {
  this->thumbLabel = label;
  this->Repaint();
}

void Slider::SetPosX(){
  int px = curValue/(upperBound - lowerBound);
  posX = px * sliderLength;
  this->Repaint();
}
// TODO CS349: Implement any other methods needed here
// TODO CS349

bool Slider::HandleMouseEvent(const MouseEvent & e){
  Point p = e.GetWhere();

  // switch(e.GetType()){
  //   case MouseEvent::mouseDown:
  //   if(this->CheckBoundary(p))  this->mouseClicked == true; 
  //   break;

  //   case MouseEvent::mouseUp:
  //   if(this->CheckBoundary(p))  this->mouseClicked == false;
  //   break;

  //   case MouseEvent::mouseDrag:
  //   break;
  // }
}

bool Slider::CheckBoundary(const Point & p){
  Rectangle bound = this->GetBounds();
  int boundY = bound.y+lineY;
  int boundX = bound.x+lineX;

 if((boundX < p.x && p.x < (boundX+sliderLength)) 
  && ((boundY-thumbHeight/2) < p.y && p.y < (boundY+thumbHeight/2))){
  return true;
 } 
 return false;
}


