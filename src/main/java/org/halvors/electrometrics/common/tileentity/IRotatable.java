package org.halvors.electrometrics.common.tileentity;

/**
 * This makes a TileEntity rotatable, it's meant to be extended.
 *
 * @author halvors
 */
public interface IRotatable {
    /**
     * Whether or not this block's orientation can be changed to a specific direction.
     */
	boolean canSetFacing(int facing);

    /**
     * The direction this block is facing.
     * @return facing
     */
	int getFacing();

    /**
     * Sets the rotation of this block.
     * @param facing
     */
	void setFacing(int facing);
}