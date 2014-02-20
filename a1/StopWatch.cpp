#include "XApplication.h"
#include "StopWatch.h"
#include <sstream>
#include <time.h>
#include <string>

using namespace std;

namespace cs349 {

  StopWatch::StopWatch(EventQueue* eventQueue, const Rectangle & bounds) : XWindow(string("Stop Watch!"), bounds) {
    this->running = false;
    this->timer = new Timer(eventQueue, 100, true, this);
    timer->Start();
    startTimeMS = 0;
    
    this->btn1 = "START";
    msec = 0;
    sec = 0;
    min = 0;
    elapsed = 0;
    // LOG(INFO) << "To exit the program, press esc key";
  }

  StopWatch::~StopWatch() {
    timer->Stop();
    delete timer;
    timer = NULL;
  }

  unsigned long StopWatch::GetCurTime() {
    struct timespec tp;
    clock_gettime(CLOCK_REALTIME, &tp);
    return tp.tv_sec * 1000 + tp.tv_nsec / 1000000;
  }
  
  void StopWatch::Paint(Graphics* g) {
// TODO CS349

    g->SetForegroundColor(0x000000);
    g->FillRect(Rectangle(0, 0, 200, 210));

    //boundary of rectangle
    g->SetForegroundColor(0xFFFFFF);
    g->DrawRect(Rectangle(5, 5, 190, 200));
    
    //numerical display
    //calculate min, sec, millisec
    if(this->startTimeMS != 0){
      int diff = this->GetCurTime()-this->startTimeMS+this->elapsed;
      this->min = diff/60000%10;
      this->sec = (diff%60000)/1000;
      this->msec = (diff%60000)%1000;
    }

    //convert int to string
    this->UpdateNumericalDisplay();
    this->UpdateAbstractDisplay();
    

    //start button
    this->UpdateBtn1Width();
    g->DrawRect(Rectangle(this->btn1_x, this->btn1_y, this->btn1_width, this->btn_height));
    g->DrawText(Point((this->btn1_x+10), (this->btn1_y+15)), this->btn1);

    //reset button
    g->DrawRect(Rectangle(this->btn2_x, this->btn2_y, this->btn2_width, this->btn_height));
    g->DrawText(Point((this->btn2_x+10), (this->btn2_y+15)), this->btn2);

  }
  
  bool StopWatch::HandleMouseEvent(const MouseEvent & e) {
    // TODO: Remove the following code and add your own event handling
    // code to handle mouse events
    // LOG_DEBUG << "Received HandleMouseEvent in StopWatch" << e;

    // FOR BTN1
    // _________________________________
    // |phase 1: !running && timer == 0|
    // |   -----   -----               |
    // |  |START| |RESET|              |
    // |   -----   -----               |
    // |_______________________________|
    //       |
    //       v
    // ________________________________
    // |phase 2: running && timer > 0 |
    // |   -----   -----              |
    // |  |PAUSE| |RESET|             |
    // |   -----   -----              |
    // |______________________________|
    //       |     ^
    //       v     |
    // ________________________________
    // |phase 3: !running && timer > 0|
    // |   ------   -----             |
    // |  |RESUME| |RESET|            |
    // |   ------   -----             |
    // |______________________________|

// TODO CS349
    switch(this->CheckBtnBoundary(e.GetWhere())){
      case 0:
      break;

      case 1:
      if(!this->IsRunning()){
        if(this->btn1 == "START"){
          this->ChangeStartPauseBtn("PAUSE");
          this->Start();
        }else{
          this->ChangeStartPauseBtn("PAUSE");
          this->Start();
          this->UpdateButton();
        }
      }else {
        this->elapsed = this->elapsed + this->GetCurTime() - this->startTimeMS;
        this->Stop();
        this->ChangeStartPauseBtn("RESUME");
        this->UpdateButton();
      }
      break;

      case 2:
      this->Stop();
      this->ResetStopWatch();
      this->ChangeStartPauseBtn("START");
      this->UpdateButton();
      break;
    }

    return true;
  }

  void StopWatch::HandleTimerEvent(const cs349::TimerEvent & e) {
    if (running) {
      Component::Repaint();
    }
  }

  void StopWatch::Start() {
    this->running = true;
    startTimeMS = GetCurTime();
  }

  void StopWatch::Stop() {
    this->running = false;
  }

  bool StopWatch::IsRunning() const {
    return running;
  }

  bool StopWatch::IsReset() const{
    if(!this->IsRunning() && this->min == 0 && this->sec == 0 && this->msec == 0){
      return true;
    }
    else{
      return false;
    }
  }

  void StopWatch::ChangeStartPauseBtn(char* str){
    this->btn1 = str;
  }

  void StopWatch::UpdateBtn1Width(){
    this->btn1_width = this->btn1.length()*10;
  }

  int StopWatch::CheckBtnBoundary(const Point & p){
    int btn_num;
      if((p.x >= this->btn1_x && p.x <= (this->btn1_x+this->btn1_width)) 
          && (p.y >= this->btn1_y && p.y <= (this->btn1_y+btn_height))){
      
        btn_num = 1;

      }else if((p.x >= this->btn2_x && p.x <= (this->btn2_x+this->btn2_width))
                && (p.y >= this->btn2_y && p.y <= (this->btn2_y+btn_height))){
        
        btn_num = 2;
      
      }else{
      
        btn_num = 0;
      
      }
    return btn_num;
  }

  void StopWatch::ResetStopWatch(){
    this->startTimeMS = 0;
    this->msec = 0;
    this->sec = 0;
    this->min = 0;
    this->elapsed = 0;
    this->UpdateNumericalDisplay(true);
    this->UpdateAbstractDisplay();
  }

  void StopWatch::UpdateNumericalDisplay(bool resume){
    Graphics* g = XWindow::GetGraphics();
    stringstream ss;
    ss << this->min;

    if(resume){
      g->SetForegroundColor(0x000000);
      g->FillRect(Rectangle(50, 20, 100, 18));
    }

    g->SetForegroundColor(0xFFFFFF);
    g->DrawText(Point(60, 35), ss.str());
    ss.str("");

    g->DrawText(Point(80, 35), ":");

    ss << this->sec;
    g->DrawText(Point(95, 35), ss.str());
    ss.str("");

    g->DrawText(Point(117, 35), ":");

    ss << this->msec;
    g->DrawText(Point(130, 35), ss.str());
  }

  void StopWatch::UpdateAbstractDisplay(){
    Graphics* g = XWindow::GetGraphics();

    int ab_min = this->min%10*10;
    int ab_sec = this->sec%60/6*10;
    int ab_msec = this->msec%1000/10;

    //abstract graphic display for min, sec, ms in order
    g->SetForegroundColor(0x000000);
    g->FillRect(Rectangle(55, 40, 100, 130));

    g->SetForegroundColor(0xFFFFFF);

    //percentage display
    stringstream ss;
    
    ss << ab_min << " %";
    g->DrawText(Point(57, 160), ss.str());
    ss.str("");
    
    ss << ab_sec << " %";
    g->DrawText(Point(94, 160), ss.str());
    ss.str("");
    
    ss << ab_msec << " %";
    g->DrawText(Point(131, 160), ss.str());
    ss.str("");

    //animation filling rectangles
    g->DrawRect(Rectangle(55, 40, 18, 100));
    g->FillRect(Rectangle(55, 40, 18, ab_min));

    g->DrawRect(Rectangle(92, 40, 18, 100));
    g->FillRect(Rectangle(92, 40, 18, ab_sec));

    g->DrawRect(Rectangle(129, 40, 18, 100));
    g->FillRect(Rectangle(129, 40, 18, ab_msec));
  }

  void StopWatch::UpdateButton(){
    Graphics* g = XWindow::GetGraphics();
    g->SetForegroundColor(0x000000);
    g->FillRect(Rectangle(this->btn1_x, this->btn1_y, this->btn1_width, this->btn_height));

    g->SetForegroundColor(0xFFFFFF);
    g->DrawRect(Rectangle(this->btn1_x, this->btn1_y, this->btn1_width, this->btn_height));

    g->DrawText(Point(this->btn1_x+10, this->btn1_y+15), this->btn1);
  }


}
