#ifndef _LABEL_H_
#define _LABEL_H_

#include "Component.h"
#include "Graphics.h"
#include "Point.h"


using namespace std;

namespace cs349{
	class Label : public Component{
		private:
			string content;
		protected:
			virtual void PaintComponent(Graphics* g);
		
		public:
			//Constructor
			Label(const string &  name, const string & content);

			//Returns the text content for this label
			string GetContent() const;

			//Sets the text content for this label
			void SetContent(const string & content);
	};
}
#endif /* _LABEL_H_ */