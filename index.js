import { NativeModules, NativeEventEmitter } from "react-native";
const { ShareReceiver } = NativeModules;
const eventEmitter = new NativeEventEmitter(ShareReceiver);

export const useShareData = () => {
  const [state, setState] = useState(null);

  useEffect(() => {
    const eventListener = eventEmitter.addListener("intent", (event) => {
      console.log(event.eventProperty); // "someValue"

      setState(event);
    });

    return () => {
      eventListener.remove();
    };
  });
  return state;
};

export default ShareReceiver;
