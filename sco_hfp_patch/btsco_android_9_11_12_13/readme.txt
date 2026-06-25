打入补丁之前,先查看对应的Change-Id是否已经有了, 如有就跳过.(例如: git log | grep I204384e368d741f42bc7a9862e400abb3dcce143)

1. device目录,根据需要soc打入.

2. hardware/realtek, 建议打入.
   hardware/rockchip/audio, 安卓9.0打入9.0目录patch.安卓10-13的,确认changeid为"I0ee18b5005ac3eea8474f41b2c2f45971e39edb5"补丁是否有在, 如有,不需要合并tinyalsa_hal_10_11_12_13, 反之手动合并此目录.

3. 安卓9,10,11 kernel是4.19版本的,打入kernel目录补丁, 而安卓12,13,打入kernel-5.10补丁.

4. system目录对应bluedroid, 安卓9.0的,打入system/bt_9目录补丁.安卓11-13的, 打入system/bt目录补丁. 
   特别需要注意的是, 安卓13 bluedroid目录已经移动到packages/modules/Bluetooth/system, 所以这里的补丁要打到此目录.

5.补丁打完了,其他注意事项见<Rockchip_Trouble_Shooting_Android_Bluetooth_CN> 1.13节.

  Enjoy!