{
  description = "Mc Music Server Development";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
  };

  outputs = { self, nixpkgs }:
  
  let 
    pkgs = nixpkgs.legacyPackages.x86_64-linux;
    libs = with pkgs; [
        maven
        jdk21 #21
        # Python
        python312Full
        python312Packages.websocket-client
      ];
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      packages = [];
      buildInputs = libs;
      LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath libs;
    };
  };
}
