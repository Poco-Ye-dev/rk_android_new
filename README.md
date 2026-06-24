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
2、rk3576 android16 wifi sdio降频到150M
```
--- a/arch/arm64/boot/dts/rockchip/rk3576.dtsi
+++ b/arch/arm64/boot/dts/rockchip/rk3576.dtsi
@@ -3959,7 +3959,7 @@ sdio: mmc@2a320000 {
                power-domains = <&power RK3576_PD_SDGMAC>;
                resets = <&cru SRST_H_SDIO>;
                reset-names = "reset";
-               rockchip,use-v2-tuning;
+               //rockchip,use-v2-tuning;
                status = "disabled";
        };

--- a/drivers/mmc/host/dw_mmc.c
+++ b/drivers/mmc/host/dw_mmc.c
@@ -1882,7 +1882,6 @@ static void dw_mci_request_end(struct dw_mci *host, struct mmc_request *mrq)
     if (host->need_xfer_timer)
         del_timer(&host->xfer_timer);

-    host->slot->mrq = NULL;
     host->mrq = NULL;
     if (!list_empty(&host->queue)) {
         slot = list_entry(host->queue.next,
@@ -1894,6 +1893,7 @@ static void dw_mci_request_end(struct dw_mci *host, struct mmc_request *mrq)
         dw_mci_start_request(host, slot);
     } else {
         dev_vdbg(host->dev, "list empty\n");
+        host->slot->mrq = NULL;

         if (host->state == STATE_SENDING_CMD11)
             host->state = STATE_WAITING_CMD11_DONE;
```

