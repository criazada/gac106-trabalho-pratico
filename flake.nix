{
  inputs = {
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};

        buildInputs = with pkgs; [ jdk8 ];
        nativeBuildInputs = with pkgs; [ ];
      in
      rec {
        devShell = pkgs.mkShell {
          inherit buildInputs nativeBuildInputs;
        };
      });
}
