package com.wesoft.rxmvvm.data.models

import com.google.gson.annotations.SerializedName

// user permission data
data class UserPermissionData(
    @SerializedName("ChargePerson") var chargePerson: Boolean,
    @SerializedName("Device") var device: UserPermissionDeviceData,
    @SerializedName("DeviceType") var deviceType: List<Int>,
    @SerializedName("Message") var message: Boolean,
    @SerializedName("Mode") var mode: UserPermissionModeData,
    @SerializedName("ModeAmount") var modeAmount: Int,
    @SerializedName("ModeAmountUsed") var modeAmountUsed: Int,
    @SerializedName("Scence") var scene: UserPermissionSceneData,
    @SerializedName("SceneAmount") var sceneAmount: Int,
    @SerializedName("SceneAmountUsed") var sceneAmountUsed: Int,
    @SerializedName("Space") var space: UserPermissionSpaceData,
    @SerializedName("Timing") var timing: Boolean
)

data class UserPermissionDeviceData(
    @SerializedName("Delete") var delete: Boolean,
    @SerializedName("Edit") var edit: Boolean
)

data class UserPermissionModeData(
    @SerializedName("Add") var add: Boolean,
    @SerializedName("Advance") var advance: Boolean,
    @SerializedName("Delete") var delete: Boolean,
    @SerializedName("Edit") var edit: Boolean
)
data class UserPermissionSceneData(
    @SerializedName("Add") var add: Boolean,
    @SerializedName("Advance") var advance: Boolean,
    @SerializedName("Delete") var delete: Boolean,
    @SerializedName("Edit") var edit: Boolean
)

data class UserPermissionSpaceData(
    @SerializedName("FloorAdd") var floorAdd: Boolean,
    @SerializedName("FloorDelete") var floorDelete: Boolean,
    @SerializedName("FloorEdit") var floorEdit: Boolean,
    @SerializedName("RoomAdd") var roomAdd: Boolean,
    @SerializedName("RoomDelete") var roomDelete: Boolean,
    @SerializedName("RoomEdit") var roomEdit: Boolean
)