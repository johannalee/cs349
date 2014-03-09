#ifndef _STOPWATCH_H_
#define _STOPWATCH_H_

#include <string.h>

#include "XWindow.h"
#include "Logging.h"
#include "Timer.h"

using namespace std;

namespace cs349 {

  /**
   * Skeleton of a StopWatch class to complete and augment for A1
   */
  class StopWatch : public XWindow, TimerListener
  {
  private:
    /**
     * Timer used to periodically update the stopwatch's display
     */
    Timer* timer;

    /**
     * The time, in milliseconds, that the stopwatch started
     */
    unsigned long startTimeMS;

    /**
     * Whether the stopwatch is currently running.
     */
    bool running;

    //button1 label
    string btn1;

    //button2 label
    const string btn2 = "RESET";

    //buttons cooridinates and sizes
    const int btn1_x = 49;
    const int btn1_y = 170;
    const int btn2_x = 109;
    const int btn2_y = 170;
    const int btn_height = 20;
    const int btn2_width = btn2.length()*10;
    int btn1_width = 50;

    //numerical display variables
    int msec;
    int sec;
    int min;
    int elapsed;


    /**
     * Returns the current time in milliseconds.
     */
    static unsigned long GetCurTime();

  public:

    /**
     * StopWatch derives from XWindow and TimerListener. These classes
     * require the following information passed in.
     * @param eventQueue The XApplication's event queue
     * @param bounds The desired location and size of this StopWatch
     * window.
     */
    StopWatch(EventQueue* eventQueue, const Rectangle & bounds);
    virtual ~StopWatch();

    /**
     * Overrides Component's HandleMouseEvent method.
     */
    virtual bool HandleMouseEvent(const MouseEvent & e);

    /**
     * Overrides Component's Paint method.
     */
    virtual void Paint(Graphics* g);

    /**
     * Overrides TimerListener's HandleTimerEvent to be able to
     * routinely update the display.
     */
    virtual void HandleTimerEvent(const cs349::TimerEvent & e);

    /**
     * Start the stopwatch
     */
    void Start();

    /**
     * Stop the stopwatch
     */
    void Stop();

    /**
     * Query whether the stopwatch is running or not.
     */
    bool IsRunning() const;

    /**
     * Check whether the stopwatch has been reset and stopped. This is for unittest
     */
    bool IsReset() const;

    /**
     * Change the label(string) of button1 (start || pause || resume).
     */    
    void ChangeStartPauseBtn(char* status);

    /**
     * According to each label for button1, it customizes the width of the button.
     */    
    void UpdateBtn1Width();

    /**
     * For mouse click event, it checks whether a certain button has been clicked or not.
     */    
    int CheckBtnBoundary(const Point & p);

    /**
     * Reset all the clock display variables and startTimeMS. Also update the label of button1.
     */    
    void ResetStopWatch();

    /**
     * Update the numerical display every millisecond.
     */    
    void UpdateNumericalDisplay(bool resume = false);

    /**
     * Update the graph which represents the percentage of elasped time every millisecond as well.
     */    
    void UpdateAbstractDisplay();

    /**
     * Update the real label view on button1.
     */    
    void UpdateButton();
  };
}

#endif /* _STOPWATCH_H_ */
