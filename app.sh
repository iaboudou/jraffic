FX_VERSION="25.0.4"
FX_DIR="$HOME/javafx"
BUILD_DIR="build"

install() {
    if [ -d "$FX_DIR" ]; then echo "JavaFX déjà installé."
        return
    fi

    curl -L -o /tmp/javafx.zip "https://download2.gluonhq.com/openjfx/$FX_VERSION/openjfx-${FX_VERSION}_linux-x64_bin-sdk.zip"
    unzip -q /tmp/javafx.zip -d "$HOME"
    mv "$HOME/javafx-sdk-$FX_VERSION" "$FX_DIR"
    rm /tmp/javafx.zip
    echo "JavaFX installé."
}

run() {
    rm -rf "$BUILD_DIR"
    mkdir -p "$BUILD_DIR"
    javac --module-path "$FX_DIR/lib" --add-modules javafx.controls -d "$BUILD_DIR" $(find src -name "*.java")
    java --enable-native-access=javafx.graphics --module-path "$FX_DIR/lib" --add-modules javafx.controls -cp "$BUILD_DIR" Main 2>&1 | grep -v 'dconf-WARNING'
}

if [ "$1" = "install" ]; then install
elif [ "$1" = "run" ]; then
    echo "==> start jraffic"
    run
else echo "Usage: $0 {install|run}"
fi