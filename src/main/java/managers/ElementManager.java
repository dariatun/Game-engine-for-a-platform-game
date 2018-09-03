/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import ids.ElementId;
import static ids.ElementId.DOOR;
import ids.Ids;
import ids.SpriteId;
import sprite.Element;
import sprite.Sprite;

/**
 *
 * @author dariatunina
 */
public class ElementManager extends BasicManager {

    /**
     * Creates a new instance of ElementManager
     *
     * @param classId the type of the element (ElementId)
     * @param fileName specific name of the file where are the initial elements
     */
    public ElementManager(Class classId, String fileName) {
        super(classId, fileName);
    }

    @Override
    public void setAdditionalPar(Sprite element) {
        if (((Element) element).getSpecificId() == DOOR) {
            ((Element) element).changeImg("Closed");
        }
    }

    @Override
    public Element getSprite(String name) {
        return (Element) this.getSpriteMap().get(name);
    }

    @Override
    public Element getSprite(Ids id) {
        Element ret = null;
        for (Element element : getSprites()) {
            if (element.getSpecificId().equals(id)) {
                ret = element;
            }
        }
        return ret;
    }

    @Override
    public Element[] getSprites() {
        return this.getSpriteMap().values().toArray(
                new Element[this.getSpriteMap().size()]);
    }

    @Override
    public void putSprite(String name, Ids id) {
        getSpriteMap().put(name, new Element(
                SpriteId.ELEMENT,
                ((ElementId) id).getImage(),
                id.getWidth(),
                id.getHeight(),
                (ElementId) id,
                ((ElementId) id).getFunc()));
    }
}
