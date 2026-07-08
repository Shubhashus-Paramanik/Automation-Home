package com.main.ajarul.dto;

public class DevicePermissionDto {
 private boolean canControl;
    private boolean canEdit;
    private boolean canDelete;
    private boolean canShare;

    
    public boolean isCanControl() {
        return canControl;
    }
    public void setCanControl(boolean canControl) {
        this.canControl = canControl;
    }
    public boolean isCanEdit() {
        return canEdit;
    }
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    public boolean isCanDelete() {
        return canDelete;
    }
    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
    public boolean isCanShare() {
        return canShare;
    }
    public void setCanShare(boolean canShare) {
        this.canShare = canShare;
    }
}
