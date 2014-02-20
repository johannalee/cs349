#include "Label.h"
#include "XWindow.h"
#include "Logging.h"

using namespace cs349;

Label::Label(const string & name, const string & content) : Component(name){
		this->content = content;
}

string Label::GetContent() const{
	return content;
}

void Label::SetContent(const string & content){
	this->content = content;
}

void Label::PaintComponent(Graphics* g){

	g->SetForegroundColor(0x000000);
	g->FillRect(Rectangle(0, 0, 200, 20));

	g->SetForegroundColor(0xFFFFFF);

	g->DrawText(Point(0, 0), this->GetContent());
}
