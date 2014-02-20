#include "MainPanel.h"
#include "TurtleGraphics.h"
#include "Button.h"
#include "Slider.h"
#include "Label.h"
#include "A2WidgetIdentifiers.h"

using namespace cs349;

class ButtonListener : public ActionListener{
public:
  long val;
  ButtonListener(){
    val = 0;
  }
  virtual void ActionPerformed(Component* c){
    val = 100;
    // TurtleGraphics::GoToStep(val);
  }
};

class SliderListener : public ValueListener{
public:
  int i;
  SliderListener(){
    i = 0;
  }
  virtual void ValueChanged(Component* c){
    LOG(INFO) << c->GetName();
  }
};
MainPanel::MainPanel()
  : Panel(MAIN_PANEL_ID)
{
  //easy to manage children panel layout
  turtleX = 45;
  listenerX = 400;
  labelHeight = 20;
  labelWidth = 200;
  sliderWidth = 400;
  sliderHeight = 150;
  sliderY1 = 60;
  sliderY2 = 170;
  buttonY = 300;
  buttonSize = 50;
  //listners
  SliderListener slistener;
  ButtonListener listener;

  this->timer = new Timer(XApplication::GetInstance()->GetEventQueue(), 100, true, this);
  // TODO CS349: Create your entire interface here. You will need to
  // modify the provided code below to lay things out the way you like

  // Note the use of unique identifiers for each widget when they are
  // constructed. See A2WidgetIdentifiers.h and the assignment
  // specification for the identifiers you should use for each of the
  // components you create. These identifiers are used for unit tests
  // to easily identify components in the interactor tree.
  // ActionListener* a;
  /**
  *turtleGraphicsPanel initialization
  **/
  Component* turtleGraphicsPanel = new Panel(TURTLE_GRAPHICS_PANEL_ID);
  Label* turtleGraphicsOutputLabel = new Label(TURTLE_GRAPHICS_OUTPUT_LABEL_ID, "TURTLE GRAPHICS OUTPUT");
  TurtleGraphics* turtle = new TurtleGraphics(TURTLE_GRAPHICS_WIDGET_ID, XApplication::GetInstance()->GetEventQueue());

  //Sets size of children components in turtleGraphicsPanel
  this->SetBounds(Rectangle(0, 0, 800, 420));
  turtleGraphicsPanel->SetBounds(Rectangle(turtleX, 0, 320, 330));
  turtleGraphicsOutputLabel->SetBounds(Rectangle(turtleX+85 , 40, labelWidth, labelHeight));
  turtle->SetBounds(Rectangle(turtleX, 60, 300, 300));

  
  //Adds children to parent component
  turtleGraphicsPanel->AddComponent(turtleGraphicsOutputLabel);
  turtleGraphicsPanel->AddComponent(turtle);
  this->AddComponent(turtleGraphicsPanel);

  //Sets visibility
  turtleGraphicsOutputLabel->SetVisible(true);
  turtle->SetVisible(true);
  turtleGraphicsPanel->SetVisible(true);

  // TODO CS349: Add other initialization code here
// TODO CS349
  /**
  *playbackPanel initialization
  **/
  Component* playbackPanel = new Panel(PLAYBACK_PANEL_ID);
  Label* playbackOutputLabel = new Label(PLAYBACK_OUTPUT_LABEL_ID, "PLAYBACK RATE");
  Slider* playbackSlider = new Slider(PLAYBACK_SLIDER_ID, Slider::HORIZONTAL_SLIDER);

  //Sets size
  playbackPanel->SetBounds(Rectangle(listenerX, 300, 350, 200));
  playbackOutputLabel->SetBounds(Rectangle(listenerX, sliderY1, labelWidth, labelHeight));
  playbackSlider->SetBounds(Rectangle(listenerX, (sliderY1+15), sliderWidth, sliderHeight));
  playbackSlider->SetLowerBoundLabel("0.1x");
  playbackSlider->SetUpperBoundLabel("10x");
  playbackSlider->SetThumbLabel("x");
  playbackSlider->SetMinimum(0);
  playbackSlider->SetMaximum(100);

  //Adds children to parent component
  playbackPanel->AddComponent(playbackOutputLabel);
  playbackPanel->AddComponent(playbackSlider);
  this->AddComponent(playbackPanel);

  //add SliderListeners
  playbackSlider->AddValueListener(&slistener);

  //Set visibility
  playbackSlider->SetVisible(true);
  playbackOutputLabel->SetVisible(true);
  playbackPanel->SetVisible(true);

  /**
  *currentTurtleStepPanel initialization
  **/
  Component* currentTurtleStepPanel = new Panel(CURRENT_TURTLE_STEP_PANEL_ID);
  Label* currentTurtleStepLabel = new Label(CURRENT_TURTLE_STEP_LABEL_ID, "CURRENT TURTLE STEP");
  Slider* currentTurtleStepSlider = new Slider(CURRENT_TURTLE_STEP_SLIDER_ID, Slider::HORIZONTAL_SLIDER);
  currentTurtleStepSlider->SetLowerBoundLabel(" 0");
  currentTurtleStepSlider->SetUpperBoundLabel("100");

  //Sets size
  currentTurtleStepPanel->SetBounds(Rectangle(listenerX, 300, 350, 200));
  currentTurtleStepLabel->SetBounds(Rectangle(listenerX, sliderY2, labelWidth, labelHeight));
  currentTurtleStepSlider->SetBounds(Rectangle(listenerX, (sliderY2+15), sliderWidth, sliderHeight));
  currentTurtleStepSlider->SetMinimum(0);
  currentTurtleStepSlider->SetMaximum(200);

  //Adds children to parent component
  currentTurtleStepPanel->AddComponent(currentTurtleStepLabel);
  currentTurtleStepPanel->AddComponent(currentTurtleStepSlider);
  this->AddComponent(currentTurtleStepPanel);

  currentTurtleStepLabel->SetVisible(true);
  currentTurtleStepSlider->SetVisible(true);
  currentTurtleStepPanel->SetVisible(true);

  /**
  *buttonPanel
  **/
  Component* buttonPanel = new Panel(BUTTON_PANEL_ID);
  Button* goToStartButton = new Button(GO_TO_START_BUTTON_ID, "goToStartButton");
  Button* previousFrameButton = new Button(PREVIOUS_FRAME_BUTTON_ID, "previousFrameButton");
  Button* playButton = new Button(PLAY_BUTTON_ID, "playButton");
  Button* nextFrameButton = new Button(NEXT_FRAME_BUTTON_ID, "nextFrameButton");
  Button* goToEndButton = new Button(GO_TO_END_BUTTON_ID, "goToEndButton");

  //Sets buttonType
  goToStartButton->SetButtonType(Button::TO_START);
  previousFrameButton->SetButtonType(Button::BACKWARD);
  playButton->SetButtonType(Button::PLAY);
  nextFrameButton->SetButtonType(Button::FORWARD);
  goToEndButton->SetButtonType(Button::TO_END);

  //Sets size 
  goToStartButton->SetBounds(Rectangle(listenerX, buttonY, (buttonSize+20), buttonSize));
  previousFrameButton->SetBounds(Rectangle((listenerX+70), buttonY, buttonSize, buttonSize));
  playButton->SetBounds(Rectangle((listenerX+145), (buttonY-10), buttonSize, (buttonSize+20)));
  nextFrameButton->SetBounds(Rectangle((listenerX+240), buttonY, buttonSize, buttonSize));
  goToEndButton->SetBounds(Rectangle((listenerX+300), buttonY, (buttonSize+20), buttonSize));

  //Adds children to parent component
  buttonPanel->AddComponent(goToStartButton);
  buttonPanel->AddComponent(previousFrameButton);
  buttonPanel->AddComponent(playButton);
  buttonPanel->AddComponent(nextFrameButton);
  buttonPanel->AddComponent(goToEndButton);
  this->AddComponent(buttonPanel);

  //add ButtonListeners
  goToStartButton->AddActionListener(&listener);

  //Sets visibility
  goToStartButton->SetVisible(true);
  previousFrameButton->SetVisible(true);
  playButton->SetVisible(true);
  nextFrameButton->SetVisible(true);
  goToEndButton->SetVisible(true);
  buttonPanel->SetVisible(true);

  // Some simple operations to show the turtle when starting up
  // This is an entirely random set of instructions
  // Once started up, try typing this into the console:
  // (turtle-star (find-turtle-graphics-widget) 300 23)
  // The 300 argument is the length of the edges of the star, in
  // pixels
  // The 23 argument is the number of points for the star
  turtle->RotateInDegrees(45);
  turtle->Forward(100, true);
  turtle->RotateInDegrees(135);
  turtle->Forward(65, true);
  turtle->RotateInDegrees(135);
  turtle->Forward(100, true);
  turtle->RotateInDegrees(150);
  turtle->Forward(30, true);
}

