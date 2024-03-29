// A1 Unit Tests

#include "gtest/gtest.h"
#include "StopWatch.h"
#include "XApplication.h"

using namespace cs349;

TEST(A1UnitTestDemos, Construction) {
  StopWatch* watch = new StopWatch(XApplication::GetInstance()->GetEventQueue(), Rectangle(0, 0, 200, 210));
  EXPECT_FALSE(watch == NULL);
  EXPECT_FALSE(watch->IsRunning());

  EventQueue* queue = XApplication::GetInstance()->GetEventQueue();
  EXPECT_FALSE(queue == NULL);
  queue->ClearEventQueue(); // Need to clear the event queue of any
                            // events our watch window has generated,
                            // or else subsequent processing of those
                            // events will fail miserably (i.e., core
                            // dump) if events from the event queue
                            // are processed after the watch pointer
                            // is deleted below.

  delete watch;
}

TEST(A1UnitTestDemos, Interaction) {
  StopWatch* watch = new StopWatch(XApplication::GetInstance()->GetEventQueue(), Rectangle(0, 0, 200, 210));
  EXPECT_FALSE(watch == NULL);

  // Create a synthetic mouse event to test whether watch responds to it
  // or not. Note that this assumes that clicking in the location
  // specified amounts to pressing the start/stop button. Your actual
  // interaction will likely be different, making this test useless.
  // However, this should provide a template for how to do unit tests
  // for interaction.
  EXPECT_FALSE(watch->GetParentWindow() == NULL);
  MouseEvent* e = new MouseEvent(watch->GetParentWindow(), MouseEvent::mouseDown, Point(79, 179));

  XApplication* app = XApplication::GetInstance();
  // EventQueue* queue = XApplication::GetInstance()->GetEventQueue();
  EventQueue* queue = app->GetEventQueue();

  watch->SetVisible(true);
  XFlush(app->GetXDisplay());
  
  EXPECT_FALSE(queue == NULL);
  EXPECT_FALSE(watch->IsRunning());
  
  queue->AddEventToQueue(e);
  
  unsigned int max_num_tries_to_flush_queue = 1000000;
  while(max_num_tries_to_flush_queue-- > 0){
    queue->ProcessNextEvent();
    XFlush(app->GetXDisplay());  
  }
  EXPECT_TRUE(watch->IsRunning());

  //PAUSE TEST
  MouseEvent* f = new MouseEvent(watch->GetParentWindow(), MouseEvent::mouseDown, Point(79, 179));
  queue->AddEventToQueue(f);
  max_num_tries_to_flush_queue = 2000000;
  while(max_num_tries_to_flush_queue-- > 0){
    queue->ProcessNextEvent();
    XFlush(app->GetXDisplay());  
  }
  EXPECT_FALSE(watch->IsRunning());

  //RESUME(UNPAUSE) TEST
  MouseEvent* g = new MouseEvent(watch->GetParentWindow(), MouseEvent::mouseDown, Point(79, 179));
  queue->AddEventToQueue(g);
  max_num_tries_to_flush_queue = 1000000;
  while(max_num_tries_to_flush_queue-- > 0){
    queue->ProcessNextEvent();
    XFlush(app->GetXDisplay());
  }
  EXPECT_TRUE(watch->IsRunning());

  //RESET TEST
  MouseEvent* h = new MouseEvent(watch->GetParentWindow(), MouseEvent::mouseDown, Point(140, 181));
  queue->AddEventToQueue(h);
  max_num_tries_to_flush_queue = 3000000;
  while(max_num_tries_to_flush_queue-- > 0){
    queue->ProcessNextEvent();
    XFlush(app->GetXDisplay());
  }
  EXPECT_TRUE(watch->IsReset());
  // EXPECT_FALSE(watch->IsRunning());

  //START TEST AFTER RESET
  MouseEvent* i = new MouseEvent(watch->GetParentWindow(), MouseEvent::mouseDown, Point(79, 179));
  queue->AddEventToQueue(i);
  max_num_tries_to_flush_queue = 3000000;
  while(max_num_tries_to_flush_queue-- > 0){
    queue->ProcessNextEvent();
    XFlush(app->GetXDisplay());
  }
  EXPECT_TRUE(watch->IsRunning());

  //RESET WITHOUT PAUSE
  MouseEvent* j = new MouseEvent(watch->GetParentWindow(), MouseEvent::mouseDown, Point(140, 181));
  queue->AddEventToQueue(j);
  max_num_tries_to_flush_queue = 1000000;
  while(max_num_tries_to_flush_queue-- > 0){
    queue->ProcessNextEvent();
    XFlush(app->GetXDisplay());
  }
  EXPECT_TRUE(watch->IsReset());
  EXPECT_FALSE(watch->IsRunning());

  queue->ClearEventQueue();
  delete watch;
  delete app;
  // We do not need to delete the mouse event that we created, because
  // it will be deleted automatically by the EventQueue.
}
