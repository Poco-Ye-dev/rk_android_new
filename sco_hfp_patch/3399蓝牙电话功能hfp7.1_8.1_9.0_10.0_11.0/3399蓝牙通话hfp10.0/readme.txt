本文档是基于正基模块3399 10.0 调试

1、用tinyalsa_hal.zip 直接替换，或者对比打在hardware/rockchip/audio/tinyalsa_hal

2、蓝牙参考android10.0_hfp_modify.patch修改， 33999.0 现在蓝牙是作从，主控提供sync(8k) clock(512K)



音频确认channels_max = 2，最新SDK应该已更新
sdk_project@aaaaa:~/10.0_sdk/Rockchip_Android10.0_SDK_Release_20191211/kernel$ git diff  sound/soc/codecs/bt-sco.c   
diff --git a/sound/soc/codecs/bt-sco.c b/sound/soc/codecs/bt-sco.c
index d07d7be..529028b 100644
--- a/sound/soc/codecs/bt-sco.c
+++ b/sound/soc/codecs/bt-sco.c
@@ -31,14 +31,14 @@ static struct snd_soc_dai_driver bt_sco_dai[] = {
                .playback = {
                        .stream_name = "Playback",
                        .channels_min = 1,
-                       .channels_max = 1,
+                        .channels_max = 2,
                        .rates = SNDRV_PCM_RATE_8000,
                        .formats = SNDRV_PCM_FMTBIT_S16_LE,
                },
                .capture = {
                         .stream_name = "Capture",
                        .channels_min = 1,
-                       .channels_max = 1,
+                        .channels_max = 2,
                        .rates = SNDRV_PCM_RATE_8000,
                        .formats = SNDRV_PCM_FMTBIT_S16_LE,
                },
@@ -48,14 +48,14 @@ static struct snd_soc_dai_driver bt_sco_dai[] = {
                .playback = {
                        .stream_name = "Playback",
                        .channels_min = 1,
-                       .channels_max = 1,
+                        .channels_max = 2,
                        .rates = SNDRV_PCM_RATE_8000 | SNDRV_PCM_RATE_16000,
                        .formats = SNDRV_PCM_FMTBIT_S16_LE,
                },
                .capture = {
                         .stream_name = "Capture",
                        .channels_min = 1,
-                       .channels_max = 1,
+                        .channels_max = 2,
                        .rates = SNDRV_PCM_RATE_8000 | SNDRV_PCM_RATE_16000,
                        .formats = SNDRV_PCM_FMTBIT_S16_LE,
                },