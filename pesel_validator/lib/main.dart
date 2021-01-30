import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final appTitle = 'PESEL validator';

    return MaterialApp(
      title: appTitle,
      theme: ThemeData(
        primarySwatch: Colors.amber,
        primaryColor: Colors.yellowAccent[200],
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text(appTitle),
        ),
        body: PeselValidator(),
          backgroundColor: Colors.amber[50],
      ),
    );
  }
}

class PeselValidator extends StatefulWidget {
  @override
  _ValidatorState createState() => _ValidatorState();
}

class _ValidatorState extends State<PeselValidator> {
  final _globalKey = GlobalKey<FormState>();
  final _errorLengthMessage = 'PESEL number must have 11 digits';
  final _errorMonthMessage = 'Your 3rd and 4th digit must be between [1, 12] '
      'or [20, 32] or [40, 52]';
  final _errorDayMessage = 'Your 5th and 6th digits must be between 1 and 31';

  final _textColor = Colors.indigo[900];
  final _fontSize = 26.0;

  final peselDigitsWeights = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
  final validatorController = TextEditingController();
  bool showResult = false;
  String checksum = "";
  String gender = "";
  String birthDate = "";

  void _validatePersonPESEL() {
    setState(() {
      String day = validatorController.text.substring(4, 6);
      String month = validatorController.text.substring(2, 4);
      birthDate = day + "." + _getYearWithMonth(int.parse(month));

      if (int.parse(validatorController.text.substring(9, 10)) % 2 != 0) {
        gender = "Male";
      } else {
        gender = "Female";
      }

      _getVerifyChecksum();
      showResult = true;
    });
  }

  String _getYearWithMonth(int month) {
    String year;
    int subtractFromMonths;
    if (month < 20) {
      year = "19";
      subtractFromMonths = 0;
    } else if (month < 40) {
      year = "20";
      subtractFromMonths = 20;
    } else {
      year = "21";
      subtractFromMonths = 40;
    }

    year += validatorController.text.substring(0, 2);
    String monthStr = (month - subtractFromMonths).toString().padLeft(2, '0');

    return monthStr + "." + year;
  }

  void _getVerifyChecksum() {
    int checksumValue = 0;
    for (int i = 0; i < peselDigitsWeights.length; i++) {
      checksumValue +=
          int.parse(validatorController.text[i]) * peselDigitsWeights[i];
    }

    int validChecksumValue = int.parse(
        validatorController.text[validatorController.text.length - 1]);
    int moduloChecksum = checksumValue % 10;
    if (moduloChecksum == 0 && moduloChecksum == validChecksumValue) {
      checksum = "valid";
    } else if (10 - moduloChecksum == validChecksumValue) {
      checksum = "valid";
    } else {
      checksum = "invalid";
    }
  }

  @override
  void dispose() {
    validatorController.dispose();
    super.dispose();
  }

  bool _validateTheMonth(int month) {
    if (month >= 1 && month <= 12) {
      return true;
    } else if (month >= 20 && month <= 32) {
      return true;
    } else if (month >= 40 && month <= 52) {
      return true;
    } else
      return false;
  }

  @override
  Widget build(BuildContext context) {
    return Form(
        key: _globalKey,
        child: Column(children: <Widget>[
          TextFormField(
            validator: (value) {
              if (value.length != 11) {
                setState(() {
                  showResult = false;
                });
                return _errorLengthMessage;
              } else if (!_validateTheMonth(
                  int.parse(validatorController.text.substring(2, 4)))) {
                setState(() {
                  showResult = false;
                });
                return _errorMonthMessage;
              } else if (int.parse(validatorController.text.substring(4, 6)) >
                  31) {
                setState(() {
                  showResult = false;
                });
                return _errorDayMessage;
              }
              return null;
            },
            controller: validatorController,
            textAlign: TextAlign.center,
            decoration: InputDecoration(
                icon: Icon(Icons.person),
                hintText: 'Enter Pesel',
                contentPadding: EdgeInsets.only(top: 30, bottom: 10)),
            maxLength: 11,
          ),
          Padding(
            padding: const EdgeInsets.all(25),
            child: ElevatedButton(
              onPressed: () {
                if (_globalKey.currentState.validate()) {
                  _validatePersonPESEL();
                }
              },
              child: Text('View information', style: TextStyle(fontSize: 20)),
            ),
          ),
          Visibility(
            visible: showResult,
            child: Column(
              children: [
                Padding(
                  padding: new EdgeInsets.only(top: 35, bottom: 8),
                  child: Text('Birthday: ' + birthDate,
                      style: TextStyle(color: _textColor, fontSize: _fontSize)),
                ),
                Padding(
                  padding: new EdgeInsets.only(bottom: 8),
                  child: Text('Gender: ' + gender,
                      style: TextStyle(color: _textColor, fontSize: _fontSize)),
                ),
                Padding(
                  padding: new EdgeInsets.only(bottom: 8),
                  child: Text('Checksum: ' + checksum,
                      style: TextStyle(color: _textColor, fontSize: _fontSize)),
                ),
              ],
            ),
          ),
        ]));
  }
}
