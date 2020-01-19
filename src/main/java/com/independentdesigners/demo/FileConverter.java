package com.independentdesigners.demo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.Cube;
import eu.mihosoft.vrl.v3d.Transform;
import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

public class FileConverter {

    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }

    public static CSG SchemToStl(File f) {
        try {
            System.out.println(f.getAbsolutePath());
            FileInputStream fis = new FileInputStream(f);
            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();
            Map<String, Tag> tagCollection = backuptag.getValue();


            short width = (Short)getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            short height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
            short length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            byte[] data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

            System.out.println(width);
            System.out.println(height);
            System.out.println(length);
            nbt.close();
            fis.close();

            CSG startcube = new Cube(1).toCSG();
            CSG result = startcube.difference(new Cube(1).toCSG());
            for (int y = 0; y < height; y++){
                for (int z = 0; z < length; z++) {
                    for (int x = 0; x < width; x++){
                        int position = (y*length+z)*width+x;
                        byte blockid = blocks[position];
                        System.out.println(blockid);
                        System.out.println(blockid == 0);
                        if (blockid != 0 && blockid != 41){
                            CSG cube = new Cube(1).toCSG();
                            result = result.union(cube.transformed((Transform.unity().translate(x,z,y))));

                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}