1、android16以后改bluedroid优先级
```
第1步：修改源码
bash
cd ~/rk3572_android16_0_express_sdk
vim packages/modules/Bluetooth/system/conf/bt_stack.conf
在文件末尾粘贴：

text
TRC_BTM=5
TRC_HCI=5
TRC_L2CAP=5
TRC_SMP=5
TRC_HID_HOST=5
VerboseLogging=true
SmpVerboseLogging=true
BtmVerboseLogging=true
L2capVerboseLogging=true
保存退出（:wq）

第2步：编译蓝牙APEX
bash
source build/envsetup.sh
lunch rk3572_express-userdebug
m com.android.bt
等5-10分钟编译完成

第3步：安装到设备
bash
adb install -r out/target/product/rk3572/system/apex/com.android.bt.apex
如果报错，换这种方式：

bash
adb root
adb remount
adb push out/target/product/rk3572/system/apex/com.android.bt.apex /system/apex/
第4步：重启生效
bash
adb reboot
```
