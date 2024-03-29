// Author: Michael Terry
#ifndef _BUTTON_H_
#define _BUTTON_H_

#include "Component.h"
#include "ActionListener.h"
#include <vector>
#include "Graphics.h"
#include "Logging.h"

using namespace std;

namespace cs349 {
  class Button : public Component {
  public:
    enum ButtonType{
        FORWARD,
        BACKWARD,
        TO_START,
        TO_END,
        PLAY,
        DEFAULT
    };

  private:
    string label;
    ButtonType buttontype;
    vector<ActionListener*> listeners;
// TODO CS349
  protected:
    /**
     * The method that does the actual painting of the component once
     * the Graphics context object has been set up by the Paint
     * method in Component.
     */
    virtual void PaintComponent(Graphics* g);

  public:
    /**
     * Creates a new instance of a Button. For this class, name is a
     * unique name to uniquely identify this Button in unit tests and
     * within the interactor tree. label is the text that is displayed
     * by this Button.
     *
     * @param name A unique name to identify this Button in debugging,
     * unit tests, and within the interactor tree
     * @param label The text shown in this button, if it is a
     * text-based Button
     */
    Button(const string & name, const string & label);

    /**
     * Adds a listener to this button, which will be called when the
     * button is pressed. Button is not responsible for the memory
     * management of the ActionListener -- external code is
     * responsible for removing the listener and deleting it when it
     * is no longer needed.
     */
    void AddActionListener(ActionListener* l);

    /**
     * Removes the specified ActionListener from the Button.
     */
    void RemoveActionListener(ActionListener* l);

    /**
     * Returns the text label for this Button
     */
    string GetLabel() const;
    /**
     * Sets the text label for this button
     */
    void SetLabel(const string & label);

    // TODO CS349: Add any other necessary methods or overrides here
// TODO CS349
    //Returns the type of button image
    ButtonType GetButtonType() const;

    //Sets the type of button image
    void SetButtonType(ButtonType buttontype);
  };
}


#endif /* _BUTTON_H_ */
