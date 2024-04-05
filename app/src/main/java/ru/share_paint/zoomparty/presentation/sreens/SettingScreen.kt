package ru.share_paint.zoomparty.presentation.sreens


//@Composable
//fun SettingScreen(serviceViewModel: ServiceViewModel, navController: NavHostController) {
//    val beginSession = stringResource(R.string.return_main_screen)
//    val connectToGame = stringResource(R.string.return_main_screen)
//    var isMasterProfile by remember { mutableStateOf(Configuration.profileDevice == BluetoothWorkProfile.MASTER) }
//    var textButton by remember { mutableStateOf(if (isMasterProfile) beginSession else connectToGame) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = sUiPadding.dp, end = sUiPadding.dp, top = nUiPadding.dp, bottom = nUiPadding.dp)
//            .verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        SelectMasterSlave(callback = { value ->
//            isMasterProfile = value
//            Configuration.setProfileDevice(if (isMasterProfile) BluetoothWorkProfile.MASTER else BluetoothWorkProfile.SLAVE)
//            textButton = if (isMasterProfile) beginSession else connectToGame
//        })
//
//        Spacer(modifier = Modifier.size(largePadding.dp))
//        if (isMasterProfile.not()) SelectBTDevice()
//
//        Spacer(modifier = Modifier.weight(1f))
//        StartButton() {
//            val device = Configuration.getSelectedDevice()
//            val workProfile = if (isMasterProfile) BluetoothWorkProfile.MASTER else BluetoothWorkProfile.SLAVE
//            navController.navigate(Route.Game.name)
//            serviceViewModel.saveSettingToSharedPref(workProfile, device)
//        }
//    }
//}
//
//
//@Composable
//fun SelectMasterSlave(callback: (Boolean) -> Unit) {
//    val radioOptions = listOf(BluetoothWorkProfile.MASTER.mName, BluetoothWorkProfile.SLAVE.mName)
//    val selected = if (Configuration.profileDevice == BluetoothWorkProfile.MASTER) 0 else 1
//    var selectedOption by remember { mutableStateOf(radioOptions[selected]) }
//    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(text = stringResource(R.string.mode_of_device), style = styleLargeText)
//        Spacer(modifier = Modifier.size(sUiPadding.dp))
//        Row(horizontalArrangement = Arrangement.SpaceAround) {
//            radioOptions.forEach { choiceName ->
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    RadioButton(
//                        modifier = Modifier.size(supPadding.dp),
//                        selected = (choiceName == selectedOption),
//                        onClick = {
//                            selectedOption = choiceName
//                            val isMasterProfile = choiceName == BluetoothWorkProfile.MASTER.mName
//                            callback.invoke(isMasterProfile)
//                        }
//                    )
//                    Text(
//                        text = choiceName,
//                        fontSize = fontSizeLarge.sp,
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(start = nUiPadding.dp)
//                    )
//                    Spacer(modifier = Modifier.size(sUiPadding.dp))
//                }
//            }
//        }
//    }
//}
//
//@SuppressLint("MissingPermission")
//@Composable
//fun SelectBTDevice() {
//    var nameDevice: String = stringResource(R.string.no_name)
//    var macDevice = stringResource(R.string._00_00_00_00_00_00)
//    val lastDevice = Configuration.getSelectedDevice()
//    if (lastDevice != null) {
//        nameDevice = lastDevice.name
//        macDevice = lastDevice.address
//    }
//    val fullName = "$nameDevice : $macDevice"
//
//    var openSelectBtDevice by remember { mutableStateOf(false) }
//    var selectedDevice by remember { mutableStateOf(nameDevice) }
//
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(text = "Подключиться к мастер-устройству:", style = styleAboutTextBold)
//    }
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
//        Text(text = fullName, style = styleAboutText)
//        IconButton(onClick = { openSelectBtDevice = !openSelectBtDevice }) {
//            Icon(Icons.Filled.List, contentDescription = "Select")
//        }
//    }
//    if (openSelectBtDevice) {
//        var nSelected = Configuration.getIndexSelectedDevice()
//        if (nSelected == -1) nSelected = 0
//        DialogSelectBTDevice(
//            title = "Cписок мастер-устройств",
//            subTitle = null,
//            radioOptions = Configuration.boundedDevices,
//            nSelectedElement = nSelected,
//            onDismissRequest = { openSelectBtDevice = !openSelectBtDevice },
//            onConfirmation = { device ->
//                selectedDevice = device.name
//                openSelectBtDevice = !openSelectBtDevice
//                Configuration.setLastDevice(device)
//            }
//        )
//    }
//}

//@SuppressLint("MissingPermission")
//@Composable // Принимает на вход список DeviceItem
//fun DialogSelectBTDevice(
//    title: String?,
//    subTitle: String?,
//    radioOptions: List<BluetoothDevice>,
//    nSelectedElement: Int,
//    onDismissRequest: () -> Unit,
//    onConfirmation: (BluetoothDevice) -> Unit,
//) {
//    if (radioOptions.isEmpty()) {
//        Toast.makeText(LocalContext.current, "Доверенных устройств не найдено. Проверьте подключение bluetooth", Toast.LENGTH_LONG).show()
//        return
//    }
//    val selected = if (nSelectedElement > radioOptions.size - 1) 0 else nSelectedElement
//    var selectedOption by remember { mutableStateOf(radioOptions[selected]) }
//
//    val cHeight = getHeightDialogWindow(radioOptions.size)
//
//    Dialog(
//        onDismissRequest = { onDismissRequest() },
//        properties = DialogProperties(usePlatformDefaultWidth = false)
//    ) {
//        // Draw a rectangle shape with rounded corners inside the dialog
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(cHeight.dp)
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//                    .padding(nPadding.dp),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.Start,
//            ) {
//                if (title != null) {
//                    Text(
//                        modifier = Modifier
//                            .align(Alignment.CenterHorizontally)
//                            .padding(sUiPadding.dp),
//                        text = title,
//                        fontSize = fontSizeSmall.sp
//                    )
//                }
//                if (subTitle != null) {
//                    Text(
//                        modifier = Modifier.align(Alignment.CenterHorizontally),
//                        text = subTitle,
//                        fontSize = fontSizeSmall.sp
//                    )
//                }
//                Spacer(modifier = Modifier.size(20.dp))
//                radioOptions.forEach { choiceName ->
//                    val nameDevice = choiceName.name ?: stringResource(R.string.no_name)
//                    val macDevice = choiceName.address ?: stringResource(R.string._00_00_00_00_00_00)
//                    val fullName = "$nameDevice : $macDevice"
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        RadioButton(
//                            selected = (choiceName == selectedOption),
//                            onClick = { selectedOption = choiceName }
//                        )
//                        Text(
//                            text = fullName,
//                            fontSize = fontSizeSmall.sp,
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
//                ) {
//                    TextButton(
//                        onClick = { onDismissRequest() },
//                        modifier = Modifier.padding(8.dp),
//                    ) {
//                        Text(stringResource(id = R.string.dismiss_dialog))
//                    }
//                    TextButton(
//                        onClick = { onConfirmation(selectedOption) },
//                        modifier = Modifier.padding(8.dp),
//                    ) {
//                        Text(stringResource(id = R.string.confirm_dialog))
//                    }
//                }
//            }
//
//        }
//    }
//}

fun getHeightDialogWindow(count: Int) = 225 + count * 40