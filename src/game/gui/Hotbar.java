package game.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.Input;
import engine.Main;
import game.items.DirtItem;
import game.items.GrassItem;
import game.items.Item;
import game.items.ItemDrop;
import game.items.StoneItem;
import game.level.scenery.Scenery;
import game.level.tiles.Tile;

public class Hotbar extends Rectangle {

    private static final long serialVersionUID = 1L;

    private Input input;

    private int slotCount;
    private int slotSpacing = 42;

    private List<Slot> slots = new ArrayList<Slot>();

    private Container container;

    public Hotbar(Input input, int slotCount) {
        setBounds(x, y, slotSpacing * slotCount, slotSpacing);

        this.input = input;
        this.slotCount = slotCount;

        for (int i = 0; i < slotCount; i++) {
            slots.add(new Slot(input, i * slotSpacing + ((Main.WIDTH / 2) - ((slotCount * slotSpacing) / 2)) + 4,
                    Main.HEIGHT - slotSpacing));

            if (i == 0) {
                slots.get(0).setActive(true);
            }
        }

        setSlot(0, new GrassItem(Item.grass, 0, 0), 256);
        setSlot(1, new DirtItem(Item.dirt, 0, 0), 256);
        setSlot(2, new StoneItem(Item.stone, 0, 0), 256);
    }

    private void setSlot(int slot, Item item, int amount) {
        if (slot >= 0 && slot < slotCount) {
            item.x = slots.get(slot).x;
            item.y = slots.get(slot).y;
            slots.get(slot).setCurrentItem(item);
            slots.get(slot).setCurrentAmount(amount);
        } else {
            return;
        }
    }

    public void setActiveSlot(int slot) {
        if (slot >= 0 && slot < slotCount) {
            for (int i = 0; i < slotCount; i++) {
                slots.get(i).setActive(false);
            }
            slots.get(slot).setActive(true);
        } else {
            return;
        }
    }

    public void tick() {
        for (int i = 0; i < slotCount; i++) {
            slots.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < slotCount; i++) {
            slots.get(i).render(g);
        }
    }

    public Tile getSelectedItemTile() {
        for (int i = 0; i < slotCount; i++) {
            if (slots.get(i).isActive() && slots.get(i).getCurrentItem() != null) {
                return slots.get(i).getCurrentItem().getTile();
            }
        }
        return null;
    }

    public Scenery getSelectedItemScenery() {
        for (int i = 0; i < slotCount; i++) {
            if (slots.get(i).isActive() && slots.get(i).getCurrentItem() != null) {
                return slots.get(i).getCurrentItem().getScenery();
            }
        }
        return null;
    }

    public int getSelectedItemAmount() {
        for (int i = 0; i < slotCount; i++) {
            if (slots.get(i).isActive() && slots.get(i).getCurrentItem() != null) {
                return slots.get(i).getCurrentAmount();
            }
        }
        return 0;
    }

    public void setSelectedItemAmount(int amount) {
        for (int i = 0; i < slotCount; i++) {
            if (slots.get(i).isActive() && slots.get(i).getCurrentItem() != null) {
                slots.get(i).setCurrentAmount(amount);
            }
        }
    }

    public int getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public void addItem(ItemDrop itemDrop) {
        for (int i = 0; i < slotCount; i++) {
            if (slots.get(i).getCurrentItem() != null
                    && slots.get(i).getCurrentItem().getType() == itemDrop.getItem().getType()) {
                if (slots.get(i).getCurrentItem().getType() == itemDrop.getItem().getType()) {
                    slots.get(i).setCurrentAmount(slots.get(i).getCurrentAmount() + itemDrop.getAmount());
                    return;
                }
            } else {
                if (slots.get(i).getCurrentItem() == null) {
                    setSlot(i, itemDrop.getItem(), itemDrop.getAmount());
                    return;
                }
            }
        }
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public int getSlotSpacing() {
        return slotSpacing;
    }

}
