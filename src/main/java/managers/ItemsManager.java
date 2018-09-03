/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import ids.Ids;
import ids.ItemId;
import ids.SpriteId;
import sprite.Item;
import sprite.Sprite;

/**
 *
 * @author dariatunina
 */
public class ItemsManager extends BasicManager {

    /**
     * Creates a new instance of ItemsManager
     *
     * @param classId the type of the sprite (ItemId)
     * @param fileName specific name of the file where are the initial items
     */
    public ItemsManager(Class classId, String fileName) {
        super(classId, fileName);
    }

    @Override
    public Item[] getSprites() {
        return this.getSpriteMap().values().toArray(
                new Item[this.getSpriteMap().size()]);
    }

    @Override
    public void putSprite(String name, Ids id) {
        getSpriteMap().put(name, new Item(
                SpriteId.ITEM,
                ((ItemId) id).getImage(),
                id.getWidth(),
                id.getHeight(),
                (ItemId) id,
                ((ItemId) id).getFunc()));
    }

    @Override
    public void setAdditionalPar(Sprite sprite) {
    }

}
