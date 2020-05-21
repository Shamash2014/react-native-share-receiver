import { NativeModules, NativeEventEmitter } from "react-native";
import { useState, useEffect } from "react";
const { ShareReceiver } = NativeModules;
const eventEmitter = new NativeEventEmitter(ShareReceiver);

export const useShareData = () => {
  const [state, setState] = useState(null);

  useEffect(() => {}, []);

  return state;
};

export default ShareReceiver;
