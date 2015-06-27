package org.halvors.electrometrics.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.halvors.electrometrics.Reference;
import org.halvors.electrometrics.common.tileentity.IRotatable;
import org.halvors.electrometrics.common.tileentity.TileEntityMachine;
import org.halvors.electrometrics.common.util.Orientation;
import org.halvors.electrometrics.common.util.render.DefaultIcon;
import org.halvors.electrometrics.common.util.render.Renderer;

public class BlockMachine extends BlockBasic {
    private String name;

    @SideOnly(Side.CLIENT)
    private IIcon baseIcon;

    @SideOnly(Side.CLIENT)
    private IIcon[] icons = new IIcon[16];

    protected BlockMachine(String name, Material material) {
        super(material);

        this.name = name;

        setBlockName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMachine();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        baseIcon = iconRegister.registerIcon(Reference.PREFIX + name);

        Renderer.loadDynamicTextures(iconRegister, name, icons, DefaultIcon.getAll(baseIcon));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof IRotatable) {
            IRotatable rotatable = (IRotatable) tileEntity;

            //boolean active = MekanismUtils.isActive(world, x, y, z);

            return icons[Orientation.getBaseOrientation(side, rotatable.getFacing())];
        }

        return baseIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[side];
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof IRotatable) {
            IRotatable rotatable = (IRotatable) tileEntity;

            int side = MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            int height = Math.round(entity.rotationPitch);
            int change = 3;

            if (rotatable.canSetFacing(0) && rotatable.canSetFacing(1)) {
                if (height >= 65) {
                    change = 1;
                } else if (height <= -65) {
                    change = 0;
                }
            }

            if (change != 0 && change != 1) {
                switch (side) {
                    case 0: change = 2; break;
                    case 1: change = 5; break;
                    case 2: change = 3; break;
                    case 3: change = 4; break;
                }
            }

            rotatable.setFacing((short) change);
        }
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        ForgeDirection[] valid = new ForgeDirection[6];

        if (tileEntity instanceof IRotatable) {
            IRotatable rotatable = (IRotatable) tileEntity;

            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                if (rotatable.canSetFacing(direction.ordinal())) {
                    valid[direction.ordinal()] = direction;
                }
            }
        }

        return valid;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof IRotatable) {
            IRotatable rotatable = (IRotatable) tileEntity;

            if (rotatable.canSetFacing(axis.ordinal())) {
                rotatable.setFacing((short) axis.ordinal());

                return true;
            }
        }

        return super.rotateBlock(world, x, y, z, axis);
    }
}