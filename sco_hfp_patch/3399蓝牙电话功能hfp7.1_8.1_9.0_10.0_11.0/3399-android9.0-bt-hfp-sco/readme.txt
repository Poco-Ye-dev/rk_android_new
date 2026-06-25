1、用tinyalsa_hal-android-9.0.zip 直接替换，或者对比打在hardware/rockchip/audio/tinyalsa_hal

2、蓝牙参考android9.0_hfp_modify.patch修改， 33999.0 现在蓝牙是作从，主控提供sync(8k) clock(512K)

3. cat /proc/asound/cards 
要有rockchip,bt声卡

kernel$ git diff arch/arm64/boot/dts/rockchip/rk3399-tve1205g.dts
diff --git a/arch/arm64/boot/dts/rockchip/rk3399-tve1205g.dts b/arch/arm64/boot/dts/rockchip/rk3399-tve1205g.dts
index 91d12f4..c975c43 100644
--- a/arch/arm64/boot/dts/rockchip/rk3399-tve1205g.dts
+++ b/arch/arm64/boot/dts/rockchip/rk3399-tve1205g.dts
@@ -205,10 +205,12 @@
                simple-audio-card,bitclock-inversion = <1>;
                simple-audio-card,mclk-fs = <256>;
                simple-audio-card,name = "rockchip,bt";
                simple-audio-card,cpu {
                        sound-dai = <&i2s1>;
                };
                simple-audio-card,codec {
                        sound-dai = <&bt_sco>;
                };
        };

		

