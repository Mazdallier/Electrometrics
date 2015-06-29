package org.halvors.electrometrics.common.tileentity.component;

import net.minecraft.nbt.NBTTagCompound;
import org.halvors.electrometrics.common.component.IComponent;
import org.halvors.electrometrics.common.tileentity.INetworkable;

public interface ITileEntityComponent extends IComponent, INetworkable {
    void readFromNBT(NBTTagCompound nbtTags);

    void writeToNBT(NBTTagCompound nbtTags);
}